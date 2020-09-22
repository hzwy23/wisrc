package com.wisrc.sales.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisrc.sales.basic.ConfigEstimateProperties;
import com.wisrc.sales.webapp.enity.*;
import com.wisrc.sales.webapp.vo.*;
import com.wisrc.sales.webapp.vo.saleEstimate.SaleEstimatePageVo;
import com.wisrc.sales.webapp.dao.EstimateDao;
import com.wisrc.sales.webapp.query.GetEstimateApprovalQuery;
import com.wisrc.sales.webapp.service.EstimateService;
import com.wisrc.sales.webapp.service.SalePlanCommonService;
import com.wisrc.sales.webapp.service.externalService.MskuService;
import com.wisrc.sales.webapp.service.externalService.SysManageService;
import com.wisrc.sales.webapp.utils.DateUtil;
import com.wisrc.sales.webapp.utils.PageData;
import com.wisrc.sales.webapp.utils.Result;
import com.wisrc.sales.webapp.utils.Time;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(transactionManager = "retailSalesTransactionManager")
@Slf4j
public class EstimateServiceImpl implements EstimateService {

    @Autowired
    private EstimateDao estimateDao;

    @Autowired
    private MskuService mskuService;

    @Autowired
    private SysManageService sysManageService;

    @Autowired
    private ConfigEstimateProperties configEstimateProperties;

    @Autowired
    private SalePlanCommonService salePlanCommonService;

    @Override
    public Result saveEstimate(EstimateEnity estimateEnity) {

        // todo  为什么不用主键关不用uuid生成，另外的表为什么不用外键关联？？  这里由之前的开发自己来改，不明白他为什么这样做，怕他有自己的逻辑实现，省得改出冲突
//        Result mskuResult = mskuService.getMskuInfo(estimateEnity.getCreateUser(), estimateEnity.getShopId(), estimateEnity.getMskuId(), 1);
//        if (mskuResult.getCode() == 200) {
//            Map resultMap = (Map) mskuResult.getData();
//            List<Object> objects = (List<Object>) resultMap.get("mskuList");
//            if (objects != null && objects.size() > 0) {
//                Map estimateMap = (Map) objects.get(0);
//                estimateEnity.setCommodityId((String) estimateMap.get("id"));
//            } else {
//                return Result.failure(390, "不存在此商品无法新增预估", null);
//            }
//        } else {
//            return Result.failure(390, "商品查询失败无法新增", null);
//        }
////      检查含油不允许新增数据(销售预估只允许新增明天以后的计划)
//        Result checkResult = checkInsertDetailList(estimateEnity.getEstimateDetailList(), estimateEnity.getCommodityId(), estimateEnity.getCreateUser());
//        if (checkResult.getCode() != 200) {
//            return checkResult;
//        }
//        Result employeeResult = sysManageService.getEmployeeId();
//        if (employeeResult.getCode() == 200) {
//            Map map = (Map) employeeResult.getData();
//            estimateEnity.setChargeEmployeeId((String) map.get("employeeId"));
//            estimateEnity.setUpdateEmployeeId((String) map.get("employeeId"));
//        }
////      获取msku有关信息(包括商品id)
////      主键Id由商品Id和用户联合组成
//        // todo  现在有了历史数据  所以不能直接用commodityId作为estimateId
//        String estimateId = estimateEnity.getCommodityId();
//        estimateEnity.setEstimateId(estimateId);
//        EstimateEnity estimate = estimateDao.getById(estimateId);
////      如果此用户该商品有销售预估计划则执行修改逻辑
//        if (estimate != null) {
//            estimateEnity.setUpdateFlag(1);
//            estimateDao.updateEstimate(estimateEnity);
//        }
////      如果此用户该商品没有销售预估计划则执行新增逻辑
//        else {
//            estimateEnity.setUpdateFlag(1);
//            estimateDao.savaEstimate(estimateEnity);
//        }
//        List<EstimateDetailEnity> estimateDetailEnities = estimateEnity.getEstimateDetailList();
//        for (EstimateDetailEnity enity : estimateDetailEnities) {
//            String uuid = estimateId + DateUtil.convertDateToStr(enity.getEstimateDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT);
//            enity.setUuid(uuid);
//            EstimateDetailEnity estimateDetailEnity = estimateDao.getEstimateDetailById(enity.getUuid());
//            enity.setEstimateId(estimateId);
////          如果此用户该商品同一天有记录则修改
//            if (estimateDetailEnity != null) {
//                estimateDao.updateEstimateDetail(enity);
//            }
////          如果此用户该商品同一天没有记录则新增
//            else {
//                enity.setUpdateFlag(1);
//                estimateDao.addEstimateDetail(enity);
//            }
//        }
        return Result.success();
    }

    private Result checkInsertDetailList(List<EstimateDetailEnity> estimateDetailList, String commodityId, String createUser) {
        String estimateId = commodityId;
        String uuids = "";
        for (EstimateDetailEnity estimateDetailEnity : estimateDetailList) {
            String date = DateUtil.convertDateToStr(estimateDetailEnity.getEstimateDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT);
            String uuid = estimateId + date;
            estimateDetailEnity.setUuid(uuid);
            uuids += "'" + uuid + "'" + ",";
        }
        if (uuids.endsWith(",")) {
            int index = uuids.lastIndexOf(",");
            uuids = uuids.substring(0, index);
        }
        HashMap<String, Integer> map = new HashMap<>();
        List<EstimateDetailEnity> detailEnityList = estimateDao.getAllDetailByUUids(uuids);
        for (EstimateDetailEnity estimateDetailEnity : detailEnityList) {
            map.put(estimateDetailEnity.getUuid(), estimateDetailEnity.getEstimateNumber());
        }
        Date nowDate = new Date();
        String dateStr = DateUtil.convertDateToStr(nowDate, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
        nowDate = DateUtil.convertStrToDate(dateStr, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
        Date minDate = DateUtil.addDate("dd", 1, nowDate);
        for (EstimateDetailEnity estimateDetailEnity : estimateDetailList) {

            Integer originalNum = map.get(estimateDetailEnity.getUuid());
            Integer nowNum = estimateDetailEnity.getEstimateNumber();
            if (originalNum == null) {
                if (minDate.after(estimateDetailEnity.getEstimateDate())) {
                    return Result.failure(390, "不允许修改当前时间及以前的数据", null);
                } else {
                    continue;
                }
            } else {
                if (originalNum == nowNum) {
                    continue;
                }
                if (minDate.after(estimateDetailEnity.getEstimateDate())) {
                    return Result.failure(390, "不允许修改当前时间及以前的数据", null);
                }
            }
        }
        return Result.success();
    }

    private Result checkDetailList(List<EstimateDetailEnity> estimateDetailList, String estimateId) {
        String uuids = "";
        for (EstimateDetailEnity estimateDetailEnity : estimateDetailList) {
            String date = DateUtil.convertDateToStr(estimateDetailEnity.getEstimateDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT);
            String uuid = estimateId + date;
            estimateDetailEnity.setUuid(uuid);
            uuids += "'" + uuid + "'" + ",";
        }
        if (uuids.endsWith(",")) {
            int index = uuids.lastIndexOf(",");
            uuids = uuids.substring(0, index);
        }
        HashMap<String, Integer> map = new HashMap<>();
        List<EstimateDetailEnity> detailEnityList = estimateDao.getAllDetailByUUids(uuids);
        for (EstimateDetailEnity estimateDetailEnity : detailEnityList) {
            map.put(estimateDetailEnity.getUuid(), estimateDetailEnity.getEstimateNumber());
        }
        Date nowDate = new Date();
        String dateStr = DateUtil.convertDateToStr(nowDate, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
        nowDate = DateUtil.convertStrToDate(dateStr, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
        Date minDate = DateUtil.addDate("dd", 1, nowDate);
        for (EstimateDetailEnity estimateDetailEnity : estimateDetailList) {
            Integer originalNum = map.get(estimateDetailEnity.getUuid());
            Integer nowNum = estimateDetailEnity.getEstimateNumber();
            if (originalNum == null) {
                if (minDate.after(estimateDetailEnity.getEstimateDate())) {
                    return Result.failure(390, "不允许修改当前时间及以前的数据", null);
                } else {
                    continue;
                }
            } else {
                if (originalNum == nowNum) {
                    continue;
                }
                if (minDate.after(estimateDetailEnity.getEstimateDate())) {
                    return Result.failure(390, "不允许修改当前时间及以前的数据", null);
                }
            }
        }
        return Result.success();
    }

//    @Override
//    public LinkedHashMap findByCond(String pageNum, String pageSize, String shopId, String mskuId, String skuId, String productName, String asinId, Integer saleStatus, String userId) {
//        List<EstimateEnity> estimateEnitys = null;
//        List<SaleEstimateVo> saleEstimateVoList = new ArrayList<>();
//        HashSet<String> conditnIdSet = new HashSet<>();
//        Map<String, Object> employeeMap = new HashMap<>();
//        HashSet<String> employeeSet = new HashSet<>();
//        PageInfo info = new PageInfo();
////      调用外部服务查询满足条件的商品id
//        if (asinId != null || skuId != null || productName != null || saleStatus != null) {
//            Result mskuResult = mskuService.getMskuByCond(userId, asinId, skuId, productName, saleStatus);
//            if (mskuResult.getCode() == 200) {
//                List<Object> objects = (List<Object>) mskuResult.getData();
//                for (Object object : objects) {
//                    Map conditonMap = (Map) object;
//                    if (conditonMap != null) {
//                        if (StringUtils.isNotEmpty((String) conditonMap.get("id"))) {
//                            conditnIdSet.add((String) conditonMap.get("id"));
//                        }
//                    }
//                }
//            }
//        }
//        String comodityIds = "";
//        for (String commodityId : conditnIdSet) {
//            comodityIds += "'" + commodityId + "'" + ",";
//        }
//        if (comodityIds.endsWith(",")) {
//            int index = comodityIds.lastIndexOf(",");
//            comodityIds = comodityIds.substring(0, index);
//        }
////      分页查询
//        if (pageNum != null && pageSize != null) {
//            int startPage = Integer.parseInt(pageNum);
//            int size = Integer.parseInt(pageSize);
//            if (asinId != null || skuId != null || productName != null || saleStatus != null) {
//                if (StringUtils.isEmpty(comodityIds)) {
//                    return PageData.pack(0, 0, "estimateEnityList", saleEstimateVoList);
//                }
//                PageHelper.startPage(startPage, size);
//                estimateEnitys = estimateDao.getByCond(shopId, mskuId, comodityIds);
//                info = new PageInfo(estimateEnitys);
//            } else {
//                PageHelper.startPage(startPage, size);
//                estimateEnitys = estimateDao.getByCond(shopId, mskuId, null);
//                info = new PageInfo(estimateEnitys);
//            }
//        }
////      查询全部
//        else {
//            if (asinId != null || skuId != null || productName != null || saleStatus != null) {
//                if (StringUtils.isEmpty(comodityIds)) {
//                    return PageData.pack(0, 0, "estimateEnityList", saleEstimateVoList);
//                }
//                estimateEnitys = estimateDao.getByCond(shopId, mskuId, comodityIds);
//            } else {
//                estimateEnitys = estimateDao.getByCond(shopId, mskuId, null);
//            }
//        }
//
//        //查询审核信息
//        String estimateIds = "";
//        for (EstimateEnity o : estimateEnitys) {
//            estimateIds += "'" + o.getEstimateId() + "'" + ",";
//        }
//        if (estimateIds.endsWith(",")) {
//            int index = estimateIds.lastIndexOf(",");
//            estimateIds = estimateIds.substring(0, index);
//        }
//        estimateIds = "(" + estimateIds + ")";
//        if (estimateIds.equals("()")) {
//            estimateIds = "('')";
//        }
//        Map paraMap = new HashMap();
//        paraMap.put("estimateIds", estimateIds);
//        List<EstimateApprovalEnity> estimateApprovalEnityList = estimateDao.getEstimateApproval(paraMap);
//        Map<String, Map<String, String>> estimateApprovalMap = new HashMap();
//        for (EstimateApprovalEnity o : estimateApprovalEnityList) {
//            Map approvalMap = new HashMap();
//            approvalMap.put("directApprovStatus", o.getDirectApprovStatus());
//            approvalMap.put("managerApprovStatus", o.getManagerApprovStatus());
//            approvalMap.put("planDepartApprovStatus", o.getPlanDepartApprovStatus());
//            approvalMap.put("directApprovRemark", o.getDirectApprovRemark());
//            approvalMap.put("managerApprovRemark", o.getManagerApprovRemark());
//            approvalMap.put("planDepartApprovRemark", o.getPlanDepartApprovRemark());
//            estimateApprovalMap.put(o.getEstimateId(), approvalMap);
//        }
//
//
//        HashSet<String> mskuIdSet = new HashSet<>();
//        for (EstimateEnity enity : estimateEnitys) {
//            mskuIdSet.add(enity.getCommodityId());
//        }
//        Map<String, Object> mskuMap = salePlanCommonService.getMskuMap(mskuIdSet);
//        HashSet<String> modifyUserIdSet = new HashSet<>();//修改人id集合
//        for (EstimateEnity enity : estimateEnitys) {
//            String commodityId = enity.getCommodityId();
//            SaleEstimateVo saleVo = new SaleEstimateVo();
//            Map voMap = (Map) mskuMap.get(commodityId);
//            if (voMap == null) {
//                voMap = new HashMap();
//            }
//            saleVo.setAsinId((String) voMap.get("asin"));
//            saleVo.setEstimateId(enity.getEstimateId());
//            saleVo.setMskuId(enity.getMskuId());
//            saleVo.setShopId(enity.getShopId());
//            saleVo.setShopName((String) voMap.get("shopName"));
//            saleVo.setSkuId((String) voMap.get("skuId"));
//            saleVo.setProductName((String) voMap.get("productName"));
//            saleVo.setMskuStatus((String) voMap.get("salesStatusDesc"));
//            saleVo.setModifyUser(enity.getModifyUser());
//            saleVo.setCommodityId(commodityId);
//            saleVo.setChargeEmployeeId((String) voMap.get("userId"));
//            saleVo.setCreateUser(enity.getCreateUser());
//            Date date = DateUtil.convertStrToDate(enity.getModifyTime(), DateUtil.DATETIME_FORMAT);
//            saleVo.setModifyTime(DateUtil.convertDateToStr(date, DateUtil.DATETIME_FORMAT));
//
//            saleVo.setShopSellerId(enity.getShopSellerId());
//            Map<String, String> approvalMap = estimateApprovalMap.get(enity.getEstimateId());
//            if (approvalMap != null) {
//                saleVo.setDirectApprovStatus(approvalMap.get("directApprovStatus"));
//                saleVo.setManagerApprovStatus(approvalMap.get("managerApprovStatus"));
//                saleVo.setPlanDepartApprovStatus(approvalMap.get("planDepartApprovStatus"));
//                saleVo.setDirectApprovRemark(approvalMap.get("directApprovRemark"));
//                saleVo.setManagerApprovRemark(approvalMap.get("managerApprovRemark"));
//                saleVo.setPlanDepartApprovRemark(approvalMap.get("planDepartApprovRemark"));
//            } else {
//                saleVo.setDirectApprovStatus("");
//                saleVo.setManagerApprovStatus("");
//                saleVo.setPlanDepartApprovStatus("");
//                saleVo.setDirectApprovRemark("");
//                saleVo.setManagerApprovRemark("");
//                saleVo.setPlanDepartApprovRemark("");
//            }
//
//            saleEstimateVoList.add(saleVo);
//            employeeSet.add(saleVo.getChargeEmployeeId());
//            modifyUserIdSet.add(saleVo.getModifyUser());
//        }
//        Map<String, ExecutiveDirectorVO> executiveDirectorVOMap =
//                salePlanCommonService.getExecutiveDirector(employeeSet);//获得类目主管以及负责人信息
//        Map<String, UserInfoVO> userInfoVOMap =
//                salePlanCommonService.getUserInfo(modifyUserIdSet);//获取修改用户信息
//        for (SaleEstimateVo vo : saleEstimateVoList) {
//            ExecutiveDirectorVO executiveDirectorVO = executiveDirectorVOMap.get(vo.getChargeEmployeeId());
//            if (executiveDirectorVO == null) {
//                //如果没有对应的人员，那么应该把id置空
//                vo.setChargeEmployeeId(null);
//            } else {
//                vo.setChargePerson(executiveDirectorVO.getChargeEmployeeName());
//                vo.setDirectorEmployeeId(executiveDirectorVO.getExecutiveDirectorId());
//                vo.setDirectorEmployeeName(executiveDirectorVO.getExecutiveDirectorName());
//            }
//            UserInfoVO userInfoVO = userInfoVOMap.get(vo.getModifyUser());
//            if (userInfoVO == null) {
//                //如果没有对应的人员，那么应该把id置空 虽然不太可能
//                vo.setModifyUser(null);
//            } else {
//                vo.setModifyUserName(userInfoVO.getUserName());
//            }
//        }
//        if (pageNum != null && pageSize != null) {
//            return PageData.pack(info.getTotal(), info.getPages(), "estimateEnityList", saleEstimateVoList);
//        } else {
//            return PageData.pack(-1, -1, "estimateEnityList", saleEstimateVoList);
//        }
//    }

    //增加权限控制
    @Override
    public Result findByCond(SaleEstimatePageVo saleEstimatePageVo, String userId) {
        if (userId == null) {
            return new Result(9991, "未登录！无权查看相关信息", null);
        }
        List<EstimateEnity> estimateEnitys = null;
        List<SaleEstimateVo> saleEstimateVoList = new ArrayList<>();
        HashSet<String> conditnIdSet = new HashSet<>();
        Map<String, Object> employeeMap = new HashMap<>();
        HashSet<String> employeeSet = new HashSet<>();
        PageInfo info = new PageInfo();
//      调用外部服务查询满足条件的商品id,并顺带做了userId的mksu权限控制
        String comodityIds = "";
        Result mskuResult = getComodityIds(userId, saleEstimatePageVo.getAsinId(), saleEstimatePageVo.getSkuId(), saleEstimatePageVo.getProductName(), saleEstimatePageVo.getSaleStatus());
        if (mskuResult.getCode() != 200) {
            return mskuResult;
        } else {
            comodityIds = (String) mskuResult.getData();
        }
//      分页查询
        Timestamp asOfDate = Time.getCurrentTimestamp();
        if (saleEstimatePageVo.getAsOfDate() != null) {
            asOfDate = Timestamp.valueOf(saleEstimatePageVo.getAsOfDate());
        }
        if (saleEstimatePageVo.getPageNum() != null && saleEstimatePageVo.getPageSize() != null) {
            int startPage = saleEstimatePageVo.getPageNum();
            int size = saleEstimatePageVo.getPageSize();
            if (StringUtils.isEmpty(comodityIds)) {
                return Result.success(PageData.pack(0, 0, "estimateEnityList", saleEstimateVoList));
            }
            PageHelper.startPage(startPage, size);
            estimateEnitys = estimateDao.getByCond(saleEstimatePageVo.getShopId(), saleEstimatePageVo.getMskuId(), comodityIds, asOfDate);
            info = new PageInfo(estimateEnitys);
        }
//      查询全部
        else {
            if (StringUtils.isEmpty(comodityIds)) {
                return Result.success(PageData.pack(0, 0, "estimateEnityList", saleEstimateVoList));
            }
            estimateEnitys = estimateDao.getByCond(saleEstimatePageVo.getShopId(), saleEstimatePageVo.getMskuId(), comodityIds, asOfDate);
        }

        //查询审核信息
        String estimateIds = "";
        for (EstimateEnity o : estimateEnitys) {
            estimateIds += "'" + o.getEstimateId() + "'" + ",";
        }
        if (estimateIds.endsWith(",")) {
            int index = estimateIds.lastIndexOf(",");
            estimateIds = estimateIds.substring(0, index);
        }
        estimateIds = "(" + estimateIds + ")";
        if (estimateIds.equals("()")) {
            estimateIds = "('')";
        }
        GetEstimateApprovalQuery paraMap = new GetEstimateApprovalQuery();
        paraMap.setEstimateIds(estimateIds);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (saleEstimatePageVo.getAsOfDate() == null) {
            paraMap.setAsOfDate(Time.getCurrentDate());
        } else {
            try {
                paraMap.setAsOfDate(new java.sql.Date(sdf.parse(saleEstimatePageVo.getAsOfDate()).getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
                return Result.failure(400, "日期格式错误", "");
            }
        }
        List<EstimateApprovalEnity> estimateApprovalEnityList = estimateDao.getEstimateApproval(paraMap);
        Map<String, Map<String, String>> estimateApprovalMap = new HashMap();
        for (EstimateApprovalEnity o : estimateApprovalEnityList) {
            Map approvalMap = new HashMap();
            approvalMap.put("directApprovStatus", o.getDirectApprovStatus());
            approvalMap.put("managerApprovStatus", o.getManagerApprovStatus());
            approvalMap.put("planDepartApprovStatus", o.getPlanDepartApprovStatus());
            approvalMap.put("directApprovRemark", o.getDirectApprovRemark());
            approvalMap.put("managerApprovRemark", o.getManagerApprovRemark());
            approvalMap.put("planDepartApprovRemark", o.getPlanDepartApprovRemark());
            estimateApprovalMap.put(o.getEstimateId(), approvalMap);
        }


        HashSet<String> mskuIdSet = new HashSet<>();
        for (EstimateEnity enity : estimateEnitys) {
            mskuIdSet.add(enity.getCommodityId());
        }
        Map<String, Object> mskuMap = salePlanCommonService.getMskuMap(mskuIdSet);
        HashSet<String> modifyUserIdSet = new HashSet<>();//修改人id集合
        for (EstimateEnity enity : estimateEnitys) {
            String commodityId = enity.getCommodityId();
            SaleEstimateVo saleVo = new SaleEstimateVo();
            Map voMap = (Map) mskuMap.get(commodityId);
            if (voMap == null) {
                voMap = new HashMap();
            }
            saleVo.setAsinId((String) voMap.get("asin"));
            saleVo.setEstimateId(enity.getEstimateId());
            saleVo.setMskuId(enity.getMskuId());
            saleVo.setShopId(enity.getShopId());
            saleVo.setShopName((String) voMap.get("shopName"));
            saleVo.setSkuId((String) voMap.get("skuId"));
            saleVo.setProductName((String) voMap.get("productName"));
            saleVo.setMskuStatus((String) voMap.get("salesStatusDesc"));
            saleVo.setModifyUser(enity.getModifyUser());
            saleVo.setCommodityId(commodityId);
            saleVo.setChargeEmployeeId((String) voMap.get("userId"));
            saleVo.setCreateUser(enity.getCreateUser());
            Date date = DateUtil.convertStrToDate(enity.getModifyTime(), DateUtil.DATETIME_FORMAT);
            saleVo.setModifyTime(DateUtil.convertDateToStr(date, DateUtil.DATETIME_FORMAT));

            saleVo.setShopSellerId(enity.getShopSellerId());
            Map<String, String> approvalMap = estimateApprovalMap.get(enity.getEstimateId());
            if (approvalMap != null) {
                saleVo.setDirectApprovStatus(approvalMap.get("directApprovStatus"));
                saleVo.setManagerApprovStatus(approvalMap.get("managerApprovStatus"));
                saleVo.setPlanDepartApprovStatus(approvalMap.get("planDepartApprovStatus"));
                saleVo.setDirectApprovRemark(approvalMap.get("directApprovRemark"));
                saleVo.setManagerApprovRemark(approvalMap.get("managerApprovRemark"));
                saleVo.setPlanDepartApprovRemark(approvalMap.get("planDepartApprovRemark"));
            } else {
                saleVo.setDirectApprovStatus("");
                saleVo.setManagerApprovStatus("");
                saleVo.setPlanDepartApprovStatus("");
                saleVo.setDirectApprovRemark("");
                saleVo.setManagerApprovRemark("");
                saleVo.setPlanDepartApprovRemark("");
            }

            saleEstimateVoList.add(saleVo);
            employeeSet.add(saleVo.getChargeEmployeeId());
            modifyUserIdSet.add(saleVo.getModifyUser());
        }
        Map<String, ExecutiveDirectorVO> executiveDirectorVOMap =
                salePlanCommonService.getExecutiveDirector(employeeSet);//获得类目主管以及负责人信息
        Map<String, UserInfoVO> userInfoVOMap =
                salePlanCommonService.getUserInfo(modifyUserIdSet);//获取修改用户信息
        for (SaleEstimateVo vo : saleEstimateVoList) {
            ExecutiveDirectorVO executiveDirectorVO = executiveDirectorVOMap.get(vo.getChargeEmployeeId());
            if (executiveDirectorVO == null) {
                //如果没有对应的人员，那么应该把id置空
                vo.setChargeEmployeeId(null);
            } else {
                vo.setChargePerson(executiveDirectorVO.getChargeEmployeeName());
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
        if (saleEstimatePageVo.getPageNum() != null && saleEstimatePageVo.getPageSize() != null) {
            return Result.success(PageData.pack(info.getTotal(), info.getPages(), "estimateEnityList", saleEstimateVoList));
        } else {
            return Result.success(PageData.pack(-1, -1, "estimateEnityList", saleEstimateVoList));
        }
    }

    private Result getComodityIds(String userId, String asinId, String skuId, String productName, Integer saleStatus) {
        HashSet<String> conditnIdSet = new HashSet<>();
        log.info("{}, {}, {}", userId, skuId, productName);
        Result mskuResult = mskuService.getMskuByCond(userId, asinId, skuId, productName, saleStatus);
        if (mskuResult.getCode() == 200) {
            List<Object> objects = (List<Object>) mskuResult.getData();
            for (Object object : objects) {
                Map conditonMap = (Map) object;
                if (conditonMap != null) {
                    if (StringUtils.isNotEmpty((String) conditonMap.get("id"))) {
                        conditnIdSet.add((String) conditonMap.get("id"));
                    }
                }
            }
        } else {
            return new Result(9995, "查询商品接口出错，请稍后再试，或者联系管理员", mskuResult);
        }
        String comodityIds = "";
        for (String commodityId : conditnIdSet) {
            comodityIds += "'" + commodityId + "'" + ",";
        }
        if (comodityIds.endsWith(",")) {
            int index = comodityIds.lastIndexOf(",");
            comodityIds = comodityIds.substring(0, index);
        }
        return Result.success(comodityIds);
    }

    @Override
    public Result getEstimateEnityById(String estimateId, String userId) {
        if (userId == null) {
            return new Result(9991, "未登录！无权查看相关信息", null);
        }
        EstimateEnity enity = estimateDao.getByEstimateId(estimateId);
        HashSet<String> employeeSet = new HashSet<>();
        if (enity == null) {
            return Result.success(enity);
        }
        //补充信息，并顺带做了userId的mksu权限控制
        Result mskuResult = mskuService.getMskuInfo(userId, enity.getShopId(), enity.getMskuId(), 1);
        if (mskuResult.getCode() == 200) {
            Map resultMap = (Map) mskuResult.getData();
            List<Object> objects = (List<Object>) resultMap.get("mskuList");
            if (objects != null && objects.size() > 0) {
                Map estimateMap = (Map) objects.get(0);
                enity.setSkuId((String) estimateMap.get("skuId"));
                enity.setProductName((String) estimateMap.get("productName"));
            } else {
                // 说明msku那边没有查到相应的msku，说明该商品无效或者该用户无权查看该商品
                return Result.success(null);
            }
        } else {
            return new Result(9995, "查询商品接口出错，请稍后再试，或者联系管理员", mskuResult);
        }
//      当前日期天数+1
        Date date = DateUtil.addDate("dd", 1, new Date());
//      获取当前日期月份第一天
        Date startDate = DateUtil.getFirstDayCurMonth(date);
//      获取当前日期月份最后一天
        Date endDate = DateUtil.getLastMonthDay(date);
        String currentStartDate = DateUtil.convert(startDate, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
        String currentEndDate = DateUtil.convert(endDate, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
        Date beginDate = DateUtil.convertStrToDate(currentStartDate, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
        Date overDate = DateUtil.convertStrToDate(currentEndDate, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
        List<EstimateDetailEnity> estimateDetailEnities = estimateDao.getEstimateDetailByEstimateId(estimateId, beginDate, overDate, Time.getCurrentDate());
        for (EstimateDetailEnity estimateDetailEnity : estimateDetailEnities) {
            String finalDate = DateUtil.convertDateToStr(estimateDetailEnity.getEstimateDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT);
            estimateDetailEnity.setEstimateDatailDate(finalDate);
            List<RemarkVo> remarkVoList = estimateDao.getRemarkList(estimateDetailEnity.getUuid());
            for (RemarkVo remarkVo : remarkVoList) {
                employeeSet.add(remarkVo.getEmployeeId());
            }
            estimateDetailEnity.setRemarkList(remarkVoList);
        }
        String[] employeeIds = new String[employeeSet.size()];
        Result employeeResult = sysManageService.batchEmployee(employeeSet.toArray(employeeIds));
        Map<String, Object> employeeMap = new HashMap<>();
        if (employeeResult.getCode() == 200) {
            List objects = (List) employeeResult.getData();
            for (Object product : objects) {
                Map empMap = (Map) product;
                String employeeId = (String) empMap.get("employeeId");
                String employeeName = (String) empMap.get("employeeName");
                if (employeeId != null && employeeName != null) {
                    employeeMap.put(employeeId, employeeName);
                }
            }
        }
        for (EstimateDetailEnity estimateDetailEnity : estimateDetailEnities) {
            List<RemarkVo> remarkVoList = estimateDetailEnity.getRemarkList();
            for (RemarkVo remarkVo : remarkVoList) {
                remarkVo.setEmployeeName((String) employeeMap.get(remarkVo.getEmployeeId()));
            }
        }
        enity.setEstimateDetailList(estimateDetailEnities);
        return Result.success(enity);
    }

    @Override
    public Result getEstimateEnityById(String userId, String createUser, String commodityId, String estimateMonth, java.sql.Date asOfDate) {
        if (userId == null) {
            return new Result(9991, "未登录！无权查看相关信息", null);
        }
        boolean flag = false;
        Result mskuResult = mskuService.getMskuByCond(userId, null, null, null, null);
        if (mskuResult.getCode() == 200) {
            List<Object> objects = (List<Object>) mskuResult.getData();
            for (Object object : objects) {
                Map conditonMap = (Map) object;
                if (conditonMap != null) {
                    if (commodityId.equals(conditonMap.get("id"))) {
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                return Result.success();
            }
        } else {
            return new Result(9995, "查询商品接口出错，请稍后再试，或者联系管理员", mskuResult);
        }

        HashSet<String> employeeSet = new HashSet<>();
        EstimateEnity enity = estimateDao.getByCommodityId(commodityId);
        if (enity == null) {
            return Result.success();
        }
        String estimateId = enity.getEstimateId();
        Date date = DateUtil.convertStrToDate(estimateMonth, "yyyy-MM");
        Date startDate = DateUtil.getFirstDayCurMonth(date);
        Date endDate = DateUtil.getLastMonthDay(date);
        List<EstimateDetailEnity> estimateDetailEnities = estimateDao.getEstimateDetailByEstimateId(estimateId, startDate, endDate, asOfDate);
        for (EstimateDetailEnity estimateDetailEnity : estimateDetailEnities) {
            String finalDate = DateUtil.convertDateToStr(estimateDetailEnity.getEstimateDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT);
            estimateDetailEnity.setEstimateDatailDate(finalDate);
            List<RemarkVo> remarkVoList = estimateDao.getRemarkList(estimateDetailEnity.getUuid());
            for (RemarkVo remarkVo : remarkVoList) {
                employeeSet.add(remarkVo.getEmployeeId());
            }
            estimateDetailEnity.setRemarkList(remarkVoList);
        }
        String[] employeeIds = new String[employeeSet.size()];
        Result employeeResult = sysManageService.batchEmployee(employeeSet.toArray(employeeIds));
        Map<String, Object> employeeMap = new HashMap<>();
        if (employeeResult.getCode() == 200) {
            List objects = (List) employeeResult.getData();
            for (Object product : objects) {
                Map empMap = (Map) product;
                String employeeId = (String) empMap.get("employeeId");
                String employeeName = (String) empMap.get("employeeName");
                if (employeeId != null && employeeName != null) {
                    employeeMap.put(employeeId, employeeName);
                }
            }
        }
        for (EstimateDetailEnity estimateDetailEnity : estimateDetailEnities) {
            List<RemarkVo> remarkVoList = estimateDetailEnity.getRemarkList();
            for (RemarkVo remarkVo : remarkVoList) {
                remarkVo.setEmployeeName((String) employeeMap.get(remarkVo.getEmployeeId()));
            }
        }
        enity.setEstimateDetailList(estimateDetailEnities);
        return Result.success(enity);
    }

    @Override
    public Result getProductDeatail(String shopId, String mskuId, String userId) {
//        if (userId == null) {
//            return new Result(9991, "未登录！无权查看相关信息", null);
//        }
        Result mskuResult = mskuService.getMskuInfo(userId, shopId, mskuId, 1);
        Map<String, String> map = new HashMap();
        if (mskuResult.getCode() == 200) {
            Map resultMap = (Map) mskuResult.getData();
            List<Object> objects = (List<Object>) resultMap.get("mskuList");
            if (objects != null && objects.size() > 0) {
                Map estimateMap = (Map) objects.get(0);
                map.put("skuId", (String) estimateMap.get("skuId"));
                map.put("productName", (String) estimateMap.get("productName"));
                map.put("commodityId", (String) estimateMap.get("id"));
                map.put("salesStatus", (String) estimateMap.get("salesStatus"));
            }
//            else {
//                // 说明msku那边没有查到相应的msku，说明该商品无效或者该用户无权查看该商品
//                return Result.success(null);
//            }
        } else {
            return new Result(9995, "查询商品接口出错，请稍后再试，或者联系管理员", mskuResult);
        }
        return Result.success(map);
    }

    @Override
    public Result updateEstimateAndDetail(EstimateEnityVo estimateEnityVo) {
        Result checkDetaiResult = checkDetailList(estimateEnityVo.getEstimateDetailList(), estimateEnityVo.getEstimateId());
        if (checkDetaiResult.getCode() != 200) {
            return checkDetaiResult;
        }
      /*  Result checkResult=check(estimateEnityVo);
        if(checkResult.getCode()!=200){
            return checkResult;
        }*/
        Result employeeResult = sysManageService.getEmployeeId();
        if (employeeResult.getCode() == 200) {
            Map map = (Map) employeeResult.getData();
            estimateEnityVo.setUpdateEmployeeId((String) map.get("employeeId"));
        }
        estimateDao.updateEstimateTimeAndUser(estimateEnityVo.getUpdateUser(), Time.getCurrentDateTime(), estimateEnityVo.getEstimateId(), estimateEnityVo.getUpdateEmployeeId());
        List<EstimateDetailEnity> detailEnities = estimateEnityVo.getEstimateDetailList();
        for (EstimateDetailEnity estimateDetailEnity : detailEnities) {
            String date = DateUtil.convertDateToStr(estimateDetailEnity.getEstimateDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT);
            String uuid = estimateEnityVo.getEstimateId() + date;
            estimateDetailEnity.setUuid(uuid);
            estimateDetailEnity.setEstimateId(estimateEnityVo.getEstimateId());
            EstimateDetailEnity detailEnity = estimateDao.getEstimateDetailById(uuid);
            if (detailEnity != null) {
                estimateDao.updateEstimateDetail(estimateDetailEnity);
            } else {
                estimateDao.addEstimateDetail(estimateDetailEnity);
            }
        }
        return Result.success();
    }

    @Override
    public Map getTotalNum(String commodityId, String startTime, String endTime) {
        Integer total = estimateDao.getTotalNum(commodityId, startTime, endTime);
        Map<String, Object> map = new HashMap();
        map.put("commodityId", commodityId);
        map.put("totalNum,", total);
        return map;
    }

    @Override
    public void insertRemark(RemarkEnity remarkEnity) {
        Result employeeResult = sysManageService.getEmployeeId();
        if (employeeResult.getCode() == 200) {
            Map map = (Map) employeeResult.getData();
            remarkEnity.setEmployeeId((String) map.get("employeeId"));
        }
        remarkEnity.setCreateTime(Time.getCurrentDateTime());
        remarkEnity.setEstimateDetailId(remarkEnity.getCommodityId() + remarkEnity.getEstimateDate());
        EstimateDetailEnity estimateDetailEnity = estimateDao.getEstimateDetailById(remarkEnity.getCommodityId() + remarkEnity.getEstimateDate());
        if (estimateDetailEnity == null) {
            estimateDetailEnity = new EstimateDetailEnity();
            estimateDetailEnity.setEstimateId(remarkEnity.getCommodityId() + remarkEnity.getCreateUser());
            estimateDetailEnity.setUuid(remarkEnity.getCommodityId() + remarkEnity.getEstimateDate());
            estimateDetailEnity.setEstimateDate(DateUtil.convertStrToDate(remarkEnity.getEstimateDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT));
            estimateDetailEnity.setUpdateFlag(1);
            estimateDao.addEstimateDetail(estimateDetailEnity);
        }
        remarkEnity.setUpdateFlag(1);
        estimateDao.insertRemark(remarkEnity);
    }

    @Override
    public void insertUpdateRemark(UpdateRemarkEnity updateRemarkEnity) {
        Result employeeResult = sysManageService.getEmployeeId();
        if (employeeResult.getCode() == 200) {
            Map map = (Map) employeeResult.getData();
            updateRemarkEnity.setEmployeeId((String) map.get("employeeId"));
        }
        updateRemarkEnity.setCreateTime(Time.getCurrentDateTime());
        EstimateDetailEnity estimateDetailEnity = estimateDao.getEstimateDetailById(updateRemarkEnity.getEstimateId() + updateRemarkEnity.getEstimateDate());
        if (estimateDetailEnity == null) {
            estimateDetailEnity = new EstimateDetailEnity();
            estimateDetailEnity.setEstimateId(updateRemarkEnity.getEstimateId());
            estimateDetailEnity.setUuid(updateRemarkEnity.getEstimateId() + updateRemarkEnity.getEstimateDate());
            estimateDetailEnity.setEstimateDate(DateUtil.convertStrToDate(updateRemarkEnity.getEstimateDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT));
            estimateDao.addEstimateDetail(estimateDetailEnity);
        }
        updateRemarkEnity.setEstimateDetailId(updateRemarkEnity.getEstimateId() + updateRemarkEnity.getEstimateDate());
        estimateDao.insertUpdateRemark(updateRemarkEnity);
    }

    @Override
    public List<EstimateDetailEnity> getEstimateEnityByTime(String mskuId, String shopId, String startTime, String endTime) {
        String estimateId = estimateDao.getByMskuAndShopId(mskuId, shopId);
        if (estimateId == null) {
            return null;
        }
        List<EstimateDetailEnity> estimateDetailEnityList = estimateDao.getEstimateDetailByEstimateIdAndCond(estimateId, startTime, endTime);
        for (EstimateDetailEnity estimateDetailEnity : estimateDetailEnityList) {
            estimateDetailEnity.setEstimateDatailDate(DateUtil.convertDateToStr(estimateDetailEnity.getEstimateDate(), DateUtil.DEFAULT_SHORT_DATE_FORMAT));
        }
        return estimateDetailEnityList;
    }

    @Override
    public Map getBatchEstimateDetail(List<MskuParameterVo> mskuParameterVoList) {
        Map<String, List> finalMap = new HashMap<>();
        if (mskuParameterVoList == null) {
            return null;
        }
        for (MskuParameterVo mskuParameterVo : mskuParameterVoList) {
            String id = mskuParameterVo.getId();
            String mskuId = mskuParameterVo.getMskuId();
            String shopId = mskuParameterVo.getShopId();
            String startTime = mskuParameterVo.getStartTime();
            String endTime = mskuParameterVo.getEndTime();
            String estimateId = estimateDao.getByMskuAndShopId(mskuId, shopId);
            if (estimateId == null) {
                continue;
            }
            List<EstimateDetailEnity> estimateDetailEnityList = estimateDao.getEstimateDetailByEstimateIdAndCond(estimateId, startTime, endTime);
            finalMap.put(id, estimateDetailEnityList);
        }
        return finalMap;
    }

    private Result check(EstimateEnityVo estimateEnityVo) {
        List<EstimateDetailEnity> detailEnities = estimateEnityVo.getEstimateDetailList();
        Date nowDate = new Date();
        Calendar aCalendar = Calendar.getInstance();
        String dateStr = DateUtil.convertDateToStr(nowDate, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
        nowDate = DateUtil.convertStrToDate(dateStr, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
        for (EstimateDetailEnity estimateDetailEnity : detailEnities) {
            aCalendar.setTime(nowDate);
            int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
            aCalendar.setTime(estimateDetailEnity.getEstimateDate());
            int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
            if (day2 - day1 <= 14) {
                return Result.failure(390, "没有权限无法编辑近两周内的数据", null);
            }
        }
        return Result.success();
    }
}
