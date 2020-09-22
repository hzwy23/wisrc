package com.wisrc.shipment.webapp.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.shipment.webapp.service.SmartReplenishmentService;
import com.wisrc.shipment.webapp.vo.smartReplenishmentInfo.ShowSmartReplenishmentInfoVO;
import com.wisrc.shipment.webapp.dao.SmartReplenishmentImplDao;
import com.wisrc.shipment.webapp.entity.DefaultSecurityAlertDaysInfoEntity;
import com.wisrc.shipment.webapp.entity.EarlyWarningLevelAttrEntity;
import com.wisrc.shipment.webapp.entity.SmartReplenishmentInfoEntity;
import com.wisrc.shipment.webapp.service.externalService.ExternalMskuService;
import com.wisrc.shipment.webapp.service.externalService.ExternalSalesService;
import com.wisrc.shipment.webapp.utils.Result;
import com.wisrc.shipment.webapp.utils.Time;
import com.wisrc.shipment.webapp.utils.UUIDutil;
import com.wisrc.shipment.webapp.vo.replenishmentEstimateListVO.get.GetReplenishmentEstimateListVO;
import com.wisrc.shipment.webapp.vo.replenishmentEstimateListVO.get.GetReplenishmentEstimateVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class SmartReplenishmentImplService implements SmartReplenishmentService {
    private final Logger logger = LoggerFactory.getLogger(SmartReplenishmentImplService.class);
    //fba类型
    private static final int fba_deliveryMode = 1;
    //fba类型

    @Autowired
    private ExternalMskuService externalMskuService;

    @Autowired
    private SmartReplenishmentImplDao smartReplenishmentImplDao;
    @Autowired
    private ExternalSalesService externalSalesService;

    @Override
    public Result addWarning() {
        //获取全部 推广，在售， 清仓状态下 FBA类型的正常msku
        Result mskuResult;
        try {
            mskuResult = externalMskuService.getMsku(null, null, fba_deliveryMode, null);
        } catch (Exception e) {
            logger.info("获取推广，在售， 清仓状态下的正常msku接口出错！", e);
            return new Result(9995, "获取推广，在售， 清仓状态下的正常msku接口出错！", null);
        }
        if (mskuResult.getCode() != 200) {
            return new Result(mskuResult.getCode(), "获取推广，在售， 清仓状态下的正常msku接口出错！", mskuResult);
        }
        List<Map> list = (List<Map>) mskuResult.getData();

        //获取提前预警天数  DB 的CRUD
        DefaultSecurityAlertDaysInfoEntity dSADIEntity = smartReplenishmentImplDao.getDefaultSecurityAlertDaysInfo();
        int earlyWarningDays = 0;
        int defaultSecurityStockDays = 0;
        if (dSADIEntity != null) {
            earlyWarningDays = dSADIEntity.getEarlyWarningDays();
            defaultSecurityStockDays = dSADIEntity.getDefaultSecurityStockDays();
        }

        //获取msku的销售预估 (店铺id + msku)
        Map<String, Map> maskuMap = new HashMap();
        GetReplenishmentEstimateListVO outVO = new GetReplenishmentEstimateListVO();
        List<GetReplenishmentEstimateVO> rRVOlist = new ArrayList<>();
        for (Map o : list) {
            maskuMap.put((String) o.get("id"), o);
            GetReplenishmentEstimateVO getReplenishmentEstimateVO = new GetReplenishmentEstimateVO();
            getReplenishmentEstimateVO.setId((String) o.get("id"));
            getReplenishmentEstimateVO.setShopId((String) o.get("shopId"));
            getReplenishmentEstimateVO.setMskuId((String) o.get("mskuId"));
            if (o.get("fbaOnWarehouseStockNum") == null) {
                getReplenishmentEstimateVO.setFbaOnWarehouseStockNum(0);
            } else {
                getReplenishmentEstimateVO.setFbaOnWarehouseStockNum((int) o.get("fbaOnWarehouseStockNum"));
            }
            if (o.get("fbaOnWayStockNum") == null) {
                getReplenishmentEstimateVO.setFbaOnWayStockNum(0);
            } else {
                getReplenishmentEstimateVO.setFbaOnWayStockNum((int) o.get("fbaOnWayStockNum"));
            }
            ////当前日期字符串，格式：yyyy-MM-dd
            String startTime = DateUtil.today();
            Date date = DateUtil.parse(startTime);
            int size;
            if (o.get("mskuSafetyStockDays") == null) {
                size = earlyWarningDays + defaultSecurityStockDays;
            } else {
                size = earlyWarningDays + (int) o.get("mskuSafetyStockDays");
            }
            DateTime newDate2 = DateUtil.offsetDay(date, size);
            String endTime = DateUtil.formatDate(newDate2);
            getReplenishmentEstimateVO.setStartTime(startTime);
            getReplenishmentEstimateVO.setEndTime(endTime);
            rRVOlist.add(getReplenishmentEstimateVO);
        }
        outVO.setRRVOlist(rRVOlist);
        //发送到销售预估接口，返回缺货的对象
        Result estimateResult;
        try {
            estimateResult = externalSalesService.replenishment(outVO);
            System.out.println(estimateResult);
        } catch (Exception e) {
            logger.info("销售预估接口出错!", e);
            return new Result(9995, "销售预估接口出错！", e);
        }
        if (estimateResult.getCode() != 200) {
            logger.info("销售预估接口出错!", estimateResult);
            return new Result(9995, "销售预估接口出错！", estimateResult);
        }
        Map<String, Object> dataMap = (Map<String, Object>) estimateResult.getData();
        SmartReplenishmentInfoEntity entity = new SmartReplenishmentInfoEntity();
        Timestamp createTime = Time.getCurrentTimestamp();
        List<EarlyWarningLevelAttrEntity> warningLevelList = smartReplenishmentImplDao.findWaring();
        Map<String, EarlyWarningLevelAttrEntity> warningLevelMap = getWarningLevelMap(warningLevelList);
        for (String key : dataMap.keySet()) {
            Map singleMap = maskuMap.get(key);
            Map estimateValue = (Map) dataMap.get(key);
            entity.setUuid(UUIDutil.randomUUID());
            entity.setId((String) singleMap.get("id"));
            entity.setShopId((String) singleMap.get("shopId"));
            entity.setShopName((String) singleMap.get("shopName"));
            entity.setMskuId((String) singleMap.get("mskuId"));
            entity.setMskuName((String) singleMap.get("mskuName"));
            entity.setSafetyStockDays((int) singleMap.get("safetyStockDays"));
            if (singleMap.get("mskuSafetyStockDays") == null) {
                entity.setMskuSafetyStockDays(defaultSecurityStockDays);
            } else {
                entity.setMskuSafetyStockDays((int) singleMap.get("mskuSafetyStockDays"));
            }
            entity.setAsin((String) singleMap.get("asin"));
            entity.setSkuId((String) singleMap.get("skuId"));
            entity.setProductName((String) singleMap.get("productName"));
            entity.setEmployeeName((String) singleMap.get("employeeName"));
            entity.setSalesStatusDesc((String) singleMap.get("salesStatusDesc"));
            entity.setPicture((String) singleMap.get("picture"));
            if (singleMap.get("salesStatusCd") == null) {
                entity.setSalesStatusCd(null);
            } else {
                entity.setSalesStatusCd((int) singleMap.get("salesStatusCd"));
            }
            if (singleMap.get("fbaOnWarehouseStockNum") == null) {
                entity.setFbaOnWarehouseStockNum(0);
            } else {
                entity.setFbaOnWarehouseStockNum((int) singleMap.get("fbaOnWarehouseStockNum"));
            }
            if (singleMap.get("fbaOnWayStockNum") == null) {
                entity.setFbaOnWayStockNum(0);
            } else {
                entity.setFbaOnWayStockNum((int) singleMap.get("fbaOnWayStockNum"));
            }

            if (estimateValue.get("allAvailableDay") == null) {
                entity.setAllAvailableDay(0);
            } else {
                entity.setAllAvailableDay((int) estimateValue.get("allAvailableDay"));
            }
            if (estimateValue.get("onWarehouseAvailableDay") == null) {
                entity.setOnWarehouseAvailableDay(0);
            } else {
                entity.setOnWarehouseAvailableDay((int) estimateValue.get("onWarehouseAvailableDay"));
            }
            entity.setOnWayAvailableDay(entity.getAllAvailableDay() - entity.getOnWarehouseAvailableDay());
            entity = defineWarning(entity, warningLevelList, warningLevelMap);
            entity.setCreateTime(createTime);
            smartReplenishmentImplDao.insert(entity);
        }
        return Result.success();
    }

    private Map<String, EarlyWarningLevelAttrEntity> getWarningLevelMap(List<EarlyWarningLevelAttrEntity> warningLevelList) {
        Map<String, EarlyWarningLevelAttrEntity> result = new HashMap<>();
        for (EarlyWarningLevelAttrEntity o : warningLevelList) {
            result.put(o.getEarlyWarningLevelCd(), o);
        }
        return result;
    }

    private SmartReplenishmentInfoEntity defineWarning(SmartReplenishmentInfoEntity smartReplenishmentInfoEntity, List<EarlyWarningLevelAttrEntity> warningLevelList, Map<String, EarlyWarningLevelAttrEntity> warningLevelMap) {
        //todo  该方法还不完善  等待需求更新完善
        // 【断货预警】FBA在仓天数 <= 安全库存天数
        // 【其他预警】FBA可售天数 <= 时效天数  得出最相近预警

        //在仓库存支持天数
        Integer onWarehouseAvailableDay = smartReplenishmentInfoEntity.getOnWarehouseAvailableDay();
        //安全库存天数
        Integer mskuSafetyStockDays = smartReplenishmentInfoEntity.getMskuSafetyStockDays();
        //fba总支持天数
        Integer allAvailableDay = smartReplenishmentInfoEntity.getAllAvailableDay();

        // 0:未定义规则的预警,1:断货预警,2:红单预警  这三个是数据库必有数据
        //空指针归调用的方法负责排除
        if (onWarehouseAvailableDay <= mskuSafetyStockDays) {
            smartReplenishmentInfoEntity.setEarlyWarningLevelCd(warningLevelMap.get("1").getEarlyWarningLevelCd());
            smartReplenishmentInfoEntity.setEarlyWarningLevelDesc(warningLevelMap.get("1").getEarlyWarningLevelDesc());
            return smartReplenishmentInfoEntity;
        }
        //warningLevelList是按照时效天数的顺序，时间的顺序排序的
        for (int i = 0; i < warningLevelList.size(); i++) {
            EarlyWarningLevelAttrEntity warningLevel = warningLevelList.get(i);
            if (warningLevel.getAgingDays() != null) {
                if (allAvailableDay <= warningLevel.getAgingDays()) {
                    smartReplenishmentInfoEntity.setEarlyWarningLevelCd(warningLevel.getEarlyWarningLevelCd());
                    smartReplenishmentInfoEntity.setEarlyWarningLevelDesc(warningLevel.getEarlyWarningLevelDesc());
                    return smartReplenishmentInfoEntity;
                }
            }
        }

        //普通预警（规则之外的预警）
        smartReplenishmentInfoEntity.setEarlyWarningLevelCd(warningLevelMap.get("0").getEarlyWarningLevelCd());
        smartReplenishmentInfoEntity.setEarlyWarningLevelDesc(warningLevelMap.get("0").getEarlyWarningLevelDesc());
        return smartReplenishmentInfoEntity;
    }

    @Override
    public Result updateDefaultSecurityAlertDaysInfo(Integer defaultSecurityStockDays, Integer earlyWarningDays) {
        if (defaultSecurityStockDays == null || earlyWarningDays == null) {
            return new Result(9991, "安全库存天数与预警天数为必填参数", null);
        }
        Map map = new HashMap();
        map.put("id", 1);
        map.put("defaultSecurityStockDays", defaultSecurityStockDays);
        map.put("earlyWarningDays", earlyWarningDays);
        smartReplenishmentImplDao.updateDefaultSecurityAlertDaysInfo(map);
        return Result.success();
    }

    @Override
    public Result getWarning(String shopId,
                             String earlyWarningLevelDesc,
                             Integer salesStatusCd,
                             String keyWords,
                             Integer pageNum,
                             Integer pageSize) {
        List<SmartReplenishmentInfoEntity> list = new ArrayList<>();
        if (pageNum == null || pageNum < 1 || pageSize == null || pageSize < 1) {
            list = smartReplenishmentImplDao.fuzzyQuery(shopId, earlyWarningLevelDesc, salesStatusCd, keyWords);
        } else {
            PageHelper.startPage(pageNum, pageSize);
            list = smartReplenishmentImplDao.fuzzyQuery(shopId, earlyWarningLevelDesc, salesStatusCd, keyWords);
        }
        List<ShowSmartReplenishmentInfoVO> voList = poToVO(list);
        PageInfo pageInfo = new PageInfo(list);
        Map map = new HashMap();
        long total = pageInfo.getTotal();
        int pages = pageInfo.getPages();
        map.put("total", total);
        map.put("pages", pages);
        map.put("SmartReplenishmentInfoList", voList);
        return Result.success(map);
    }

    private List<ShowSmartReplenishmentInfoVO> poToVO(List<SmartReplenishmentInfoEntity> list) {
        List<ShowSmartReplenishmentInfoVO> result = new ArrayList<>();
        for (SmartReplenishmentInfoEntity o : list) {
            ShowSmartReplenishmentInfoVO vo = new ShowSmartReplenishmentInfoVO();
            BeanUtils.copyProperties(o, vo);
            vo.setCreateTime(o.getCreateTime());
            result.add(vo);
        }
        return result;
    }

    @Override
    public Result getWaringLevel() {
        List<Map> result = smartReplenishmentImplDao.getWaringLevel();
        return Result.success(result);
    }

    @Override
    public Result getDefaultSecurityAlertDaysInfo() {
        DefaultSecurityAlertDaysInfoEntity dSADIEntity = smartReplenishmentImplDao.getDefaultSecurityAlertDaysInfo();
        return Result.success(dSADIEntity);
    }
}
