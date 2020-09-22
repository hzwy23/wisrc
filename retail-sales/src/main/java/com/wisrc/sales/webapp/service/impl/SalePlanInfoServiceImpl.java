package com.wisrc.sales.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.sales.webapp.dao.SalePlanCycleDetailsDao;
import com.wisrc.sales.webapp.dao.SalePlanInfoDao;
import com.wisrc.sales.webapp.entity.SalePlanCycleDetailsEntity;
import com.wisrc.sales.webapp.entity.SalePlanInfoEntity;
import com.wisrc.sales.webapp.service.SalePlanCommonService;
import com.wisrc.sales.webapp.service.SalePlanInfoService;
import com.wisrc.sales.webapp.service.externalService.SysManageService;
import com.wisrc.sales.webapp.utils.*;
import com.wisrc.sales.webapp.vo.DetailSalePlanVO;
import com.wisrc.sales.webapp.vo.ExecutiveDirectorVO;
import com.wisrc.sales.webapp.vo.SelectSalePlanListVO;
import com.wisrc.sales.webapp.vo.UserInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SalePlanInfoServiceImpl implements SalePlanInfoService {
    private final Logger lOgger = LoggerFactory.getLogger(SalePlanInfoServiceImpl.class);

    @Autowired
    private SalePlanCommonService salePlanCommonService;
    @Autowired
    private SalePlanInfoDao salePlanInfoDao;
    @Autowired
    private SalePlanCycleDetailsDao salePlanCycleDetailsDao;
    @Autowired
    private SysManageService sysManageService;

    @Autowired
    private SysManageService mskuService;

    @Override
    @Transactional
    public void add(DetailSalePlanVO vo) {
        String planDate = vo.getPlanDate();
        String commodityId = vo.getEntity().getCommodityId();
        Result employeeResult = sysManageService.getEmployeeId();
        Map map = (Map) employeeResult.getData();
        String shopId = vo.getEntity().getShopId();
        SalePlanInfoEntity salePlanInfoEntity = salePlanInfoDao.check(shopId, commodityId);
        SalePlanInfoEntity ele = vo.getEntity();
        List<SalePlanCycleDetailsEntity> entityList = vo.getList();
        for (SalePlanCycleDetailsEntity entity : entityList) {
            entity.setUuid(UUIDutil.randomUUID());
            entity.setWeight(vo.getWeight());
            entity.setPlanDate(vo.getPlanDate());

        }
        if (salePlanInfoEntity != null) {
            for (SalePlanCycleDetailsEntity entity : entityList) {
                entity.setSalePlanId(salePlanInfoEntity.getSalePlanId());
                salePlanCycleDetailsDao.add(entity);
            }
        } else {
            ele.setSalePlanId(UUIDutil.randomUUID());
            ele.setChargeEmployeeId((String) map.get("employeeId"));
            ele.setCreateTime(Time.getCurrentDateTime());
            ele.setModifyTime(Time.getCurrentDateTime());
            salePlanInfoDao.add(ele);
            addSalePlanCycleDetailsEntity(entityList, ele);
        }
    }

    public void addSalePlanCycleDetailsEntity(List<SalePlanCycleDetailsEntity> entityList, SalePlanInfoEntity ele) {
        for (int a = 0; a < entityList.size(); a++) {
            SalePlanCycleDetailsEntity entity = entityList.get(a);
            entity.setUuid(UUIDutil.randomUUID());
            entity.setSalePlanId(ele.getSalePlanId());
            salePlanCycleDetailsDao.add(entity);
        }
    }

    @Override
    public LinkedHashMap findByCond(int num, int size,
                                    String shopId, String msku,
                                    String asin, String stockSku,
                                    String commodityName,
                                    String salesStatusCd, String userId) {

        String commodityIdListStr = getAccessMsku(userId);

        List<SelectSalePlanListVO> list = salePlanInfoDao.findByCond(shopId, msku, commodityIdListStr);
        if (list.size() > 0) {
            handleMskuInfo(list);
        }

        List<SelectSalePlanListVO> result = filterValue(list, stockSku, asin, commodityName, salesStatusCd);

        int startIndex = 0;
        int endIndex = list.size();
        if (num > 0 && size > 0) {
            // 开始分页
            startIndex = (num - 1) * size;
            if (startIndex >= result.size()) {
                startIndex = 0;
            }
            endIndex = startIndex + size;
            if (endIndex > result.size()) {
                endIndex = result.size();
            }
        }

        int pages = 0;
        if (result.size() > 0 && size > 0) {
            pages = result.size() / size;
            if (result.size() % size > 0) {
                pages += 1;
            }
        }
        return PageData.pack(result.size(), pages, "selectSalePlanListVOList", result.subList(startIndex, endIndex));
    }

    @Override
    public LinkedHashMap findByPage(int num, int size, String userId) {
        String commodityIdListStr = getAccessMsku(userId);

        PageHelper.startPage(num, size);
        List<SelectSalePlanListVO> list = salePlanInfoDao.findByPage(commodityIdListStr);
        if (list.size() > 0) {
            handleMskuInfo(list);
        }
        PageInfo pageInfo = new PageInfo(list);
        return PageData.pack(pageInfo.getTotal(), pageInfo.getPages(), "selectSalePlanListVOList", list);
    }


    // 调用MSKU权限接口，查询用户能够访问到的MSKU信息
    private String getAccessMsku(String userId) {
        Result ret = mskuService.getAccessMsku(userId);
        if (ret == null || ret.getCode() != 200) {
            lOgger.warn("获取msku授权信息失败，请联系管理员");
            return null;
        }

        List<String> commodityList = (List<String>) ret.getData();

        return ArrayToInArguments.toInArgs(commodityList);

    }

    @Override
    public LinkedHashMap findAll(String shopId, String msku, String asin, String stockSku, String commodityName,
                                 String salesStatusCd, String userId) {

        String commodityIdListStr = getAccessMsku(userId);

        List<SelectSalePlanListVO> list = salePlanInfoDao.findByCond(shopId, msku, commodityIdListStr);

        if (list.size() > 0) {
            handleMskuInfo(list);
        }
        List<SelectSalePlanListVO> result = filterValue(list, stockSku, asin, commodityName, salesStatusCd);
        return PageData.pack(result.size(), 1, "selectSalePlanListVOList", result);
    }

    /**
     * 添加msku相关信息
     * <p>
     * 添加修改人、类目主管、负责人
     * 销售状态、asin、shopName、产品名称
     * </p>
     *
     * @param list
     */
    private void handleMskuInfo(List<SelectSalePlanListVO> list) {
        HashSet<String> employeeSet = new HashSet<>();
        HashSet<String> modifyUserIdSet = new HashSet<>();//修改人id集合
        Set<String> mskuIdSet = new HashSet();
        for (SelectSalePlanListVO vo : list) {
            mskuIdSet.add(vo.getCommodityId());
        }
        Map<String, Object> mskuMap = salePlanCommonService.getMskuMap(mskuIdSet);
        for (SelectSalePlanListVO vo : list) {
            Map voMap = (Map) mskuMap.get(vo.getCommodityId());
            if (voMap != null) {
                vo.setSalesStatusCd(voMap.get("salesStatusCd").toString());
                vo.setAsin((String) voMap.get("asin"));
                vo.setShopName((String) voMap.get("shopName"));
                vo.setStockSku((String) voMap.get("skuId"));
                vo.setCommodityName((String) voMap.get("productName"));
                vo.setCommodityStatus((String) voMap.get("salesStatusDesc"));
                vo.setChargeEmployeeId((String) voMap.get("userId"));
                modifyUserIdSet.add(vo.getModifyUser()); //获得修改人员
            }
            employeeSet.add(vo.getChargeEmployeeId());
        }
        Map<String, ExecutiveDirectorVO> executiveDirectorVOMap =
                salePlanCommonService.getExecutiveDirector(employeeSet);//获得类目主管以及负责人信息
        Map<String, UserInfoVO> userInfoVOMap =
                salePlanCommonService.getUserInfo(modifyUserIdSet);//获取修改用户信息
        for (SelectSalePlanListVO vo : list) {
            ExecutiveDirectorVO executiveDirectorVO = executiveDirectorVOMap.get(vo.getChargeEmployeeId());
            if (executiveDirectorVO == null) {
                //如果没有对应的人员，那么应该把id置空
                vo.setChargeEmployeeId(null);
            } else {
                vo.setChargeEmployeeName(executiveDirectorVO.getChargeEmployeeName());
                vo.setDirectorEmployeeId(executiveDirectorVO.getExecutiveDirectorId());
                vo.setDirectorEmployeeName(executiveDirectorVO.getExecutiveDirectorName());
            }
            UserInfoVO userInfoVO = userInfoVOMap.get(vo.getModifyUser());
            if (userInfoVO == null) {
                //如果没有对应的人员，那么应该把id置空 虽然不太可能
                vo.setModifyUser(null);
            } else {
                vo.setModifyUserName(userInfoVO.getUserName());
            }
        }
    }

    private List<SelectSalePlanListVO> filterValue(List<SelectSalePlanListVO> list, String stockSku, String asin, String commodityName, String saleStatusCd) {
        List<SelectSalePlanListVO> result = new ArrayList<>();

        for (SelectSalePlanListVO ele : list) {
            if (stockSku != null && !stockSku.trim().isEmpty() && ele.getStockSku() != null && ele.getStockSku().indexOf(stockSku) < 0) {
                continue;
            }
            if (asin != null && !asin.trim().isEmpty() && ele.getAsin().indexOf(asin) < 0) {

                continue;
            }
            if (commodityName != null && !commodityName.trim().isEmpty() && ele.getCommodityName().indexOf(commodityName) < 0) {

                continue;
            }
            if (saleStatusCd != null && !saleStatusCd.trim().isEmpty() && !ele.getSalesStatusCd().equals(saleStatusCd)) {
                continue;
            }
            result.add(ele);
        }
        return result;
    }
}
