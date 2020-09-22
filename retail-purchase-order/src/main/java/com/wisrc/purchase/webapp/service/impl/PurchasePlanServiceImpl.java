package com.wisrc.purchase.webapp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.wisrc.purchase.basic.DynamicScheduledTask;
import com.wisrc.purchase.webapp.dao.*;
import com.wisrc.purchase.webapp.dto.purchasePlan.*;
import com.wisrc.purchase.webapp.entity.*;
import com.wisrc.purchase.webapp.query.GetEstimateNumQuery;
import com.wisrc.purchase.webapp.query.PurchasePlanPageQuery;
import com.wisrc.purchase.webapp.query.SkuEstimateDateQuery;
import com.wisrc.purchase.webapp.service.*;
import com.wisrc.purchase.webapp.utils.*;
import com.wisrc.purchase.webapp.vo.purchasePlan.PlanTimeEditVo;
import com.wisrc.purchase.webapp.vo.purchasePlan.PurchasePlanPageVo;
import com.wisrc.purchase.webapp.vo.purchasePlan.PurchaseSettingEditVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(transactionManager = "retailPurchaseOrderTransactionManager", rollbackFor = Exception.class)
public class PurchasePlanServiceImpl implements PurchasePlanService {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private PurchaseSettingDao purchaseSettingDao;
    @Autowired
    private PurchaseMskuService purchaseMskuService;
    @Autowired
    private ProductHandleService productService;
    @Autowired
    private PurchasePlanInfoDao purchasePlanInfoDao;
    @Autowired
    private PurchaseCodeService purchaseCodeService;
    @Autowired
    private PurchasePlanDetailsDao purchasePlanDetailsDao;
    @Autowired
    private PlanStatusAttrDao planStatusAttrDao;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private DynamicScheduledTask dynamicScheduledTask;
    @Autowired
    private CalculateCycleAttrDao calculateCycleAttrDao;
    @Autowired
    private CalculateCycleWeekAttrDao calculateCycleWeekAttrDao;
    @Autowired
    private PurchasePlanRemarkDao purchasePlanRemarkDao;
    @Autowired
    private SupplierDateOfferDao supplierDateOfferDao;
    @Autowired
    private OutWarehouseService outWarehouseService;

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public void savePurchasePlan() {
        try {
            GetSetting setting = purchaseSettingDao.getPurchaseSetting();
            Date today = Time.getCurrentDate();
            Map<String, PurchasePlanInfoEntity> skuToInfo = new HashMap();
            List<SkuEstimateDateQuery> skuEstimateList = new ArrayList<>();

            List<String> planIds = purchasePlanInfoDao.getIdByDate(today);
            if (planIds.size() > 0) {
                try {
                    purchasePlanRemarkDao.deletePlanRemark(planIds);
                } catch (Exception e) {
                }
                purchasePlanDetailsDao.deletePurchasePlanDetails(planIds);
                purchasePlanInfoDao.deletePlanByUuid(planIds);
            }

            List<String> skuIds = purchaseMskuService.getSkuId();
            if (skuIds.size() == 0) {
                return;
            }
            skuIds = ServiceUtils.distinctList(skuIds);

            List<Integer> salesStatus = purchaseCodeService.getPlanSales();

            // 关联产品获取信息
            Map<String, Map> productSales = productService.getProductSales(skuIds);

            // 关联订单到供应商交期及报价获取信息
            GetEstimateNumQuery getEstimateNum = new GetEstimateNumQuery();
            getEstimateNum.setToday(today);
            getEstimateNum.setSalesStatus(salesStatus);
            List<GetGeneralDelivery> getGeneralDelivery = purchasePlanInfoDao.getRecentDelivery(skuIds);
            Map<String, Map<String, Object>> deliveryMap = new HashMap<>();
            List skuIdList = new ArrayList();
            skuIdList.addAll(skuIds);
            Map<String, Integer> errorMap = new HashMap<>();

            getDeliveryMap(deliveryMap, skuIdList, getGeneralDelivery);

            // 获取商品结存明细表合计
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Map<String, Integer> totalMap = warehouseService.getTotal(format.format(multipyDay(today, -1)), skuIds);

            for (String skuId : skuIds) {
                PurchasePlanInfoEntity planInfo = new PurchasePlanInfoEntity();
                String uuid = Toolbox.randomUUID();
                try {
                    planInfo.setUuid(uuid);
                    planInfo.setSkuId(skuId);
                    planInfo.setCalculateDate(today);
                    planInfo.setStockCycle(setting.getStockCycle());
                    deliveryDealer(planInfo, deliveryMap);
                    if (productSales.get(skuId) == null || productSales.get(skuId).get("safetyStockDays") == null) {
                        planInfo.setSafetyStockDays(0);
                    } else {
                        planInfo.setSafetyStockDays((Integer) productSales.get(skuId).get("safetyStockDays"));
                    }
                    if (productSales.get(skuId) == null || productSales.get(skuId).get("internationalTransportDays") == null) {
                        planInfo.setInternationalTransportDays(0);
                    } else {
                        planInfo.setInternationalTransportDays((Integer) productSales.get(skuId).get("internationalTransportDays"));
                    }
                    planInfo.setPlanDay(planInfo.getGeneralDelivery() + planInfo.getSafetyStockDays() + planInfo.getHaulageDays() + planInfo.getInternationalTransportDays());
                    planInfo.setCalculateTypeCd(1);
                    planInfo.setCalculateTime(new Timestamp(planInfo.getCalculateDate().getTime()));
                    // 暂时保存计划，待获取计划详情后再补充计划
                    skuToInfo.put(skuId, planInfo);
                    // 销量预估获取参数
                    SkuEstimateDateQuery skuEstimateDate = new SkuEstimateDateQuery();
                    skuEstimateDate.setSkuId(skuId);
                    skuEstimateDate.setEstimateDate(multipyDay(today, planInfo.getPlanDay()));
                    skuEstimateList.add(skuEstimateDate);
                } catch (Exception e) {
                    Integer num = errorMap.get(skuId);
                    if (num == null) {
                        num = 0;
                    }
                    if (num < 3) {
                        num++;
                        skuToInfo.put(skuId, planInfo);
                        errorMap.put(skuId, num);
                    }
                    e.printStackTrace();
                    continue;
                }
            }

            getEstimateNum.setSkuDate(skuEstimateList);

            // 销量预估
            Map<String, Integer> estimateNumMap = new HashMap();
            List<GetEstimateNum> estimateNumList = purchasePlanInfoDao.getEstimateNum(getEstimateNum);
            for (GetEstimateNum estimateNum : estimateNumList) {
                estimateNumMap.put(Crypto.join(estimateNum.getSkuId(), estimateNum.getEstimateDate().toString()), estimateNum.getEstimateNumber());
            }
            Set<String> skuList = skuToInfo.keySet();
            Iterator<String> iterator = skuList.iterator();
            while (iterator.hasNext()) {
                String skuId = iterator.next();
                try {
                    PurchasePlanInfoEntity planInfo = skuToInfo.get(skuId);

                    List<PurchasePlanDetailsEntity> details = new ArrayList<>();
                    purchasePlanDealer(planInfo, today, estimateNumMap, totalMap, skuId, setting, details);

                    try {
                        purchasePlanInfoDao.editOverdue(planInfo.getSkuId(), today);
                        if (details.size() > 0) {
                            purchasePlanInfoDao.savePurchasePlan(planInfo);
                            for (PurchasePlanDetailsEntity purchasePlanDetail : details) {
                                purchasePlanDetailsDao.savePurchasePlanDetails(purchasePlanDetail);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return;
                    }
                } catch (Exception e) {
                    Integer num = errorMap.get(skuId);
                    if (num == null) {
                        num = 0;
                    }
                    if (num < 3) {
                        num++;
                        skuList.add(skuId);
                    }
                    e.printStackTrace();
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return;
        }
    }

    @Override
    public Result purchasePlanPage(PurchasePlanPageVo purchasePlanPageVo) {
        try {
            PurchasePlanPageReturnDto result = new PurchasePlanPageReturnDto();
            List<PurchasePlanReturnDto> planDtoList = new ArrayList<>();

            PurchasePlanPageQuery purchasePlanPageQuery = new PurchasePlanPageQuery();
            BeanUtils.copyProperties(purchasePlanPageVo, purchasePlanPageQuery);
            if (purchasePlanPageVo.getCalculateDateStart() != null) {
                purchasePlanPageQuery.setCalculateDateStart(sdf.parse(purchasePlanPageVo.getCalculateDateStart()));
            }
            if (purchasePlanPageVo.getCalculateDateEnd() != null) {
                purchasePlanPageQuery.setCalculateDateEnd(sdf.parse(purchasePlanPageVo.getCalculateDateEnd()));
            }
            if (purchasePlanPageVo.getLastPurchaseStartDate() != null) {
                purchasePlanPageQuery.setLastPurchaseStartDate(sdf.parse(purchasePlanPageVo.getLastPurchaseStartDate()));
            }
            if (purchasePlanPageVo.getLastPurchaseEndDate() != null) {
                purchasePlanPageQuery.setLastPurchaseEndDate(sdf.parse(purchasePlanPageVo.getLastPurchaseEndDate()));
            }

            // 排序
            String sortKey = purchasePlanPageVo.getSortKey();
            if ("calculateDate".equals(sortKey)) {
                purchasePlanPageQuery.setSortKey("calculate_date");
            } else if ("lastPurchaseDate".equals(sortKey)) {
                purchasePlanPageQuery.setSortKey("suggest_date");
            } else if ("supplierId".equals(sortKey)) {
                purchasePlanPageQuery.setSortKey("supplier_id");
            } else if (sortKey != null) {
                return Result.failure(400, "无效排序参数", "");
            }
            if (sortKey != null) {
                Integer sort = purchasePlanPageVo.getSort();
                if (sort == 1) {
                    purchasePlanPageQuery.setSort("ASC");
                } else if (sort == -1) {
                    purchasePlanPageQuery.setSort("DESC");
                } else {
                    return Result.failure(400, "无效排序参数", "");
                }
            }

            if (purchasePlanPageVo.getPageNum() != null && purchasePlanPageVo.getPageSize() != null) {
                PageHelper.startPage(purchasePlanPageVo.getPageNum(), purchasePlanPageVo.getPageSize());
            }
            List<PurchasePlanPage> PurchasePlanList = purchasePlanInfoDao.purchasePlanPage(purchasePlanPageQuery);

            if (PurchasePlanList.size() <= 0) {
                return Result.success("查询结果为空");
            }
            List<String> skuIds = new ArrayList();
            List<Map> mapList = new ArrayList<>();
            List uuids = new ArrayList();
            for (PurchasePlanPage purchasePlan : PurchasePlanList) {
                skuIds.add(purchasePlan.getSkuId());
                uuids.add(purchasePlan.getUuid());
                Map map = new HashMap();
                map.put("skuId", purchasePlan.getSkuId());
                if (purchasePlan.getCalculateDate() != null) {
                    java.util.Date paraDate = DateUtil.addDate("dd", -1, purchasePlan.getCalculateDate());
                    map.put("date", DateUtil.convertDateToStr(paraDate, DateUtil.DEFAULT_SHORT_DATE_FORMAT));
                }
                mapList.add(map);
            }
            Gson gson = new Gson();
            Result result1 = outWarehouseService.getWare(gson.toJson(mapList));
            Map<String, Integer> stockMap = new HashMap<>();
            if (result1.getCode() == 200) {
                if (result1.getData() != null) {
                    List<Map> mapsList = (List<Map>) result1.getData();
                    for (Map map : mapsList) {
                        for (Object id : map.keySet()) {
                            Integer num = (Integer) map.get(id);
                            stockMap.put(id.toString(), num);
                        }
                    }
                }
            }
            List<PurchasePlanRemarkEntity> remarks = purchasePlanRemarkDao.getRemark(uuids);
            Map<String, List> remarkMap = new HashMap();
            for (PurchasePlanRemarkEntity remarkEntity : remarks) {
                if (remarkMap.get(remarkEntity.getUuid()) == null) {
                    remarkMap.put(remarkEntity.getUuid(), new ArrayList());
                }
                Map remark = new HashMap();
                remark.put("dateTime", remarkEntity.getCreateTime());
                remark.put("employeeName", remarkEntity.getCreateUser());
                remark.put("remark", remarkEntity.getRemarkDesc());
                remarkMap.get(remarkEntity.getUuid()).add(remark);
            }

            // 获取商品产品信息
            Map<String, String> pictureMap = new HashMap();
            Map<String, String> cnName = new HashMap();
            if (skuIds.size() > 0) {
                // 获取商品图片
                pictureMap = getPictureMap(skuIds);

                // 获取产品名称
                cnName = productService.getProductCN(skuIds);
            }

            for (PurchasePlanPage purchasePlan : PurchasePlanList) {
                PurchasePlanReturnDto purchasePlanReturnDto = new PurchasePlanReturnDto();
                BeanUtils.copyProperties(purchasePlan, purchasePlanReturnDto);
                purchasePlanReturnDto.setProductName(cnName.get(purchasePlanReturnDto.getSkuId()));
                purchasePlanReturnDto.setPicture(pictureMap.get(purchasePlanReturnDto.getSkuId()));
                purchasePlanReturnDto.setLastPurchaseDate(purchasePlan.getSuggestDate());
                Date date = purchasePlan.getCalculateDate();
                if (date != null) {
                    java.util.Date paraDate = DateUtil.addDate("dd", -1, date);
                    String dateStr = DateUtil.convertDateToStr(paraDate, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
                    purchasePlanReturnDto.setAvailableStock(stockMap.get(purchasePlan.getSkuId() + dateStr));
                }
                if (purchasePlanReturnDto.getAvailableStock() == null) {
                    purchasePlanReturnDto.setAvailableStock(0);
                }
                if (purchasePlan.getStatusCd() == 2) {
                    Integer suggestCount = purchasePlan.getSuggestCount();
                    if (purchasePlan.getSuggestCount() == null) {
                        suggestCount = 0;
                    }
                    purchasePlanReturnDto.setAvailableStock(purchasePlanReturnDto.getAvailableStock() + suggestCount);
                }
                purchasePlanReturnDto.setRemarks(remarkMap.get(purchasePlanReturnDto.getUuid()));

                planDtoList.add(purchasePlanReturnDto);
            }

            result.setPurchasePlans(planDtoList);
            PageInfo pageInfo = new PageInfo(PurchasePlanList);
            result.setTotal(pageInfo.getTotal());
            result.setPages(pageInfo.getPages());
            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Result getPurchasePlan(String uuid) {
        try {
            GetPurchasePlanDto result = new GetPurchasePlanDto();
            List<GetPurchasePlanDetailDto> planDtoList = new ArrayList<>();

            PurchasePlanPage pageResult = purchasePlanInfoDao.getPurchasePlan(uuid);
            if (pageResult == null) {
                return Result.failure(390, "不存在此数据", null);
            }
            BeanUtils.copyProperties(pageResult, result);

            // 获取产品名称
            List skuIds = new ArrayList();
            skuIds.add(pageResult.getSkuId());
            Map<String, String> cnName = productService.getProductCN(skuIds);
            result.setProductName(cnName.get(pageResult.getSkuId()));
            Gson gson = new Gson();
            List<Map> paramapList = new ArrayList<>();
            List<PurchasePlanDetailsEntity> planDetailList = purchasePlanDetailsDao.getPurchasePlanDetail(pageResult.getUuid());
            java.util.Date nowDate = new java.util.Date();
            String nowStr = DateUtil.convertDateToStr(nowDate, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
            nowDate = DateUtil.convertStrToDate(nowStr, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
            for (PurchasePlanDetailsEntity purchasePlan : planDetailList) {
                GetPurchasePlanDetailDto planDto = new GetPurchasePlanDetailDto();
                BeanUtils.copyProperties(purchasePlan, planDto);
                if (true) {
                    Map map = new HashMap();
                    map.put("skuId", pageResult.getSkuId());
                    Date date = purchasePlan.getPlanDate();
                    if (date != null) {
                        java.util.Date paraDate = DateUtil.addDate("dd", -1, date);
                        String dateStr = DateUtil.convertDateToStr(paraDate, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
                        map.put("date", dateStr);
                        if (nowDate.getTime() > paraDate.getTime()) {
                            paramapList.add(map);
                        }
                    }
                }

                planDtoList.add(planDto);
            }
            Result result1 = outWarehouseService.getWare(gson.toJson(paramapList));
            Map finalMap = new HashMap();
            if (result1.getCode() == 200) {
                List<Map> resultList = (List<Map>) result1.getData();
                if (resultList != null && resultList.size() > 0) {
                    for (Map map : resultList) {
                        for (Object key : map.keySet()) {
                            finalMap.put(key, map.get(key));
                        }
                    }
                }
            }
            for (GetPurchasePlanDetailDto getPurchasePlanDetailDto : planDtoList) {
                java.util.Date date = getPurchasePlanDetailDto.getPlanDate();
                if (date != null) {
                    java.util.Date paraDate = DateUtil.addDate("dd", -1, date);
                    String dateStr = DateUtil.convertDateToStr(paraDate, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
                    if (nowDate.getTime() > paraDate.getTime()) {
                        Integer availableStock = (Integer) finalMap.get(pageResult.getSkuId() + dateStr);
                        getPurchasePlanDetailDto.setAvailableStock((Integer) finalMap.get(pageResult.getSkuId() + dateStr));
                        Integer avgSale = getPurchasePlanDetailDto.getAvgSales();
                        if (availableStock == null) {
                            availableStock = 0;
                        }
                        if (avgSale == null) {
                            avgSale = 0;
                        }
                        getPurchasePlanDetailDto.setAssignBalance(availableStock - avgSale);
                    }
                }
            }
            result.setPurchasePlanDetails(planDtoList);
            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result decidePlan(List<String> uuids) {
        try {
            List<Integer> statusList = purchasePlanInfoDao.getStatus(uuids);
            for (Integer status : statusList) {
                if (status != 1) {
                    return Result.failure(400, "非法计划状态，不允许确认", "");
                }
            }

            Integer maxId = purchasePlanInfoDao.getMaxPurchaseId();
            if (maxId == null) {
                maxId = 0;
            }
            for (String uuid : uuids) {
                maxId += 1;
                String newId = purchasePlanNum() + new DecimalFormat("00000").format(maxId);
                purchasePlanInfoDao.decidePlan(uuid, newId);
            }
            return Result.success(planStatusAttrDao.getPlanStatus(2));
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failure();
        }
    }

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result cancelPlan(List uuids) {
        try {
            List<Integer> statusList = purchasePlanInfoDao.getStatus(uuids);
            for (Integer status : statusList) {
                if (status != 2) {
                    return Result.failure(400, "非法计划状态，不允许取消", "");
                }
            }
            purchasePlanInfoDao.cancelPlan(uuids);
            return Result.success(planStatusAttrDao.getPlanStatus(3));
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failure();
        }
    }

    @Override
    public Result excelPlan(PurchasePlanPageVo purchasePlanPageVo, HttpServletResponse response, HttpServletRequest request) {
        try {
            if (purchasePlanInfoDao.getDataSize() > 100000) {
                return Result.failure(400, "数据量过大，终止导出", "");
            }

            List<PlanExcelDto> result = new ArrayList<>();
            PurchasePlanPageQuery purchasePlanPageQuery = new PurchasePlanPageQuery();
            BeanUtils.copyProperties(purchasePlanPageVo, purchasePlanPageQuery);
            if (purchasePlanPageVo.getCalculateDateStart() != null) {
                purchasePlanPageQuery.setCalculateDateStart(sdf.parse(purchasePlanPageVo.getCalculateDateStart()));
            }
            if (purchasePlanPageVo.getCalculateDateEnd() != null) {
                purchasePlanPageQuery.setCalculateDateEnd(sdf.parse(purchasePlanPageVo.getCalculateDateEnd()));
            }
            if (purchasePlanPageVo.getLastPurchaseStartDate() != null) {
                purchasePlanPageQuery.setLastPurchaseStartDate(sdf.parse(purchasePlanPageVo.getLastPurchaseStartDate()));
            }
            if (purchasePlanPageVo.getLastPurchaseEndDate() != null) {
                purchasePlanPageQuery.setLastPurchaseEndDate(sdf.parse(purchasePlanPageVo.getLastPurchaseEndDate()));
            }

            List<PurchasePlanPage> PurchasePlanList = purchasePlanInfoDao.purchasePlanPage(purchasePlanPageQuery);

            List skuIds = new ArrayList();
            for (PurchasePlanPage purchasePlan : PurchasePlanList) {
                skuIds.add(purchasePlan.getSkuId());
            }

            // 获取商品产品信息
            Map<String, String> cnName = new HashMap();
            if (skuIds.size() > 0) {

                // 获取产品名称
                cnName = productService.getProductCN(skuIds);
            }

            for (PurchasePlanPage purchasePlan : PurchasePlanList) {
                PlanExcelDto purchasePlanReturnDto = new PlanExcelDto();
                BeanUtils.copyProperties(purchasePlan, purchasePlanReturnDto);
                purchasePlanReturnDto.setProductName(cnName.get(purchasePlanReturnDto.getSkuId()));
                purchasePlanReturnDto.setLastPurchaseDate(purchasePlan.getSuggestDate());
                if (purchasePlan.getStatusCd() == 2) {
                    if (purchasePlanReturnDto.getRecommendPurchase() == null) {
                        purchasePlanReturnDto.setRecommendPurchase(0);
                    }
                    purchasePlanReturnDto.setAvailableStock(purchasePlanReturnDto.getAvailableStock() + purchasePlanReturnDto.getRecommendPurchase());
                }

                result.add(purchasePlanReturnDto);
            }

            String[] title = new String[]{"库存SKU", "产品中文名", "计算日期", "截至销售日", "建议采购数", "最迟采购日期", "开始缺货日", "预计到仓日", "可用库存", "预计销量", "日均销量", "最低库存量"
                    , "通用交期", "国内运输时间", "国际运输时间", "安全库存天数", "最少起订量", "供应商", "采购计划单号", "采购订单号"};
            ExcelTools.exportExcel("采购计划", title, result, "采购计划", response, request);

            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Result planStatusSelector() {
        try {
            return Result.success(planStatusAttrDao.planStatusSelector());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Result purchaseSetting() {
        try {
            return Result.success(purchaseSettingDao.getPurchaseSetting());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result editPurchaseSetting(PurchaseSettingEditVo purchaseSettingEditVo) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            PurchaseSettingEntity purchaseSetting = new PurchaseSettingEntity();
            if (purchaseSettingEditVo.getCalculateCycleWeekCd() == null) {
                purchaseSettingEditVo.setCalculateCycleWeekCd(0);
                BeanUtils.copyProperties(purchaseSettingEditVo, purchaseSetting);
                purchaseSetting.setCalculateCycleWeekCd(null);
            } else {
                BeanUtils.copyProperties(purchaseSettingEditVo, purchaseSetting);
            }
            purchaseSetting.setDatetime(new java.sql.Time(sdf.parse(purchaseSettingEditVo.getDatetime()).getTime()));
            purchaseSettingDao.editPurchaseSetting(purchaseSetting);

            dynamicScheduledTask.restart();
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failure();
        }
    }

    @Override
    public Result getPlanTime(String uuid) {
        try {
            PlanTimeDto planTimeDto = new PlanTimeDto();
            PurchasePlanInfoEntity planTime = purchasePlanInfoDao.getPlanTime(uuid);
            BeanUtils.copyProperties(planTime, planTimeDto);
            return Result.success(planTimeDto);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    @Transactional(transactionManager = "retailPurchaseOrderTransactionManager")
    public Result editPlanTime(String uuid, PlanTimeEditVo planTimeEditVo, String userId) {
        try {
            GetSetting setting = purchaseSettingDao.getPurchaseSetting();
            List<SkuEstimateDateQuery> skuEstimateList = new ArrayList<>();

            PurchasePlanPage purchasePlan = purchasePlanInfoDao.getPurchasePlan(uuid);
            // 只有待确认状态才能进行修改
            if (purchasePlan.getStatusCd() == null || purchasePlan.getStatusCd() != 1) {
                return Result.failure(400, "无效请求", "");
            }
            String skuId = purchasePlan.getSkuId();
            List<String> skuIds = new ArrayList();
            skuIds.add(skuId);
            Date today = purchasePlan.getCalculateDate();

            List uuids = new ArrayList();
            uuids.add(uuid);
            try {
                purchasePlanRemarkDao.deletePlanRemark(uuids);
            } catch (Exception e) {
            }
            purchasePlanDetailsDao.deletePurchasePlanDetails(uuids);
            purchasePlanInfoDao.deletePurchasePlan(uuid);

            List<Integer> salesStatus = purchaseCodeService.getPlanSales();

            // 关联订单到供应商交期及报价获取信息
            GetEstimateNumQuery getEstimateNum = new GetEstimateNumQuery();
            getEstimateNum.setToday(today);
            getEstimateNum.setSalesStatus(salesStatus);
            List<GetGeneralDelivery> getGeneralDelivery = purchasePlanInfoDao.getRecentDelivery(skuIds);
            Map<String, Map<String, Object>> deliveryMap = new HashMap<>();
            List skuIdList = new ArrayList();
            skuIdList.addAll(skuIds);
            getDeliveryMap(deliveryMap, skuIdList, getGeneralDelivery);

            // 获取商品结存明细表合计
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Map<String, Integer> totalMap = warehouseService.getTotal(format.format(multipyDay(today, -1)), skuIds);

            PurchasePlanInfoEntity planInfo = new PurchasePlanInfoEntity();
            planInfo.setUuid(uuid);
            planInfo.setSkuId(skuId);
            planInfo.setCalculateDate(today);
            planInfo.setStockCycle(planTimeEditVo.getStockCycle());
            deliveryDealer(planInfo, deliveryMap);
            planInfo.setSafetyStockDays(planTimeEditVo.getSafetyStockDays());
            planInfo.setInternationalTransportDays(planTimeEditVo.getInternationalTransportDays());
            planInfo.setPlanDay(planInfo.getGeneralDelivery() + planInfo.getSafetyStockDays() + planInfo.getHaulageDays() + planInfo.getInternationalTransportDays());
            planInfo.setCalculateTypeCd(2);
            planInfo.setCalculateTime(Time.getCurrentTimestamp());
            planInfo.setModifyUser(userId);

            SkuEstimateDateQuery skuEstimateDate = new SkuEstimateDateQuery();
            skuEstimateDate.setSkuId(skuId);
            skuEstimateDate.setEstimateDate(multipyDay(today, planInfo.getPlanDay()));
            skuEstimateList.add(skuEstimateDate);
            getEstimateNum.setSkuDate(skuEstimateList);

            // 获取销量预估信息
            Map<String, Integer> estimateNumMap = new HashMap();
            List<GetEstimateNum> estimateNumList = purchasePlanInfoDao.getEstimateNum(getEstimateNum);
            for (GetEstimateNum estimateNum : estimateNumList) {
                estimateNumMap.put(Crypto.join(estimateNum.getSkuId(), estimateNum.getEstimateDate().toString()), estimateNum.getEstimateNumber());
            }

            List<PurchasePlanDetailsEntity> details = new ArrayList<>();
            purchasePlanDealer(planInfo, today, estimateNumMap, totalMap, skuId, setting, details);

            if (details.size() > 0) {
                purchasePlanInfoDao.savePurchasePlan(planInfo);
                for (PurchasePlanDetailsEntity purchasePlanDetail : details) {
                    purchasePlanDetailsDao.savePurchasePlanDetails(purchasePlanDetail);
                }
                if (details.size() < planInfo.getPlanDay()) {
                    return Result.success("详情统计缺失，缺少生成数据，请检查销量预估信息");
                }
                return Result.success();
            }
            return Result.failure(400, "无法生成计划详情，请确保销量预估信息已填写", "");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.failure();
        }
    }

    @Override
    public Result saveRemark(String remark, String uuid, String userId) {
        try {
            PurchasePlanRemarkEntity purchasePlanInfo = new PurchasePlanRemarkEntity();
            purchasePlanInfo.setRemarkId(Toolbox.randomUUID());
            purchasePlanInfo.setUuid(uuid);
            purchasePlanInfo.setRemarkDesc(remark);
            purchasePlanInfo.setCreateTime(Time.getCurrentTimestamp());
            purchasePlanInfo.setCreateUser(userId);

            purchasePlanInfoDao.saveRemark(purchasePlanInfo);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Result dayWeekSelector() {
        try {
            return Result.success(calculateCycleAttrDao.dayWeekSelector());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    @Override
    public Result weekAttrSelector() {
        try {
            List<WeekAttrSelectorDto> result = new ArrayList();

            List<CalculateCycleWeekAttrEntity> calculateCycleWeekList = calculateCycleWeekAttrDao.weekAttrSelector();
            for (CalculateCycleWeekAttrEntity calculateCycleWeekAttr : calculateCycleWeekList) {
                WeekAttrSelectorDto weekAttrSelectorDto = new WeekAttrSelectorDto();
                BeanUtils.copyProperties(calculateCycleWeekAttr, weekAttrSelectorDto);

                result.add(weekAttrSelectorDto);
            }

            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    public Date multipyDay(Date date, Integer day) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, day); //把日期往后增加一天,整数  往后推,负数往前移动
        date = new Date(calendar.getTime().getTime());

        return date;
    }

    public Map getPictureMap(List skuIds) {
        Map pictureMap = new HashMap<>();

        try {
            List productPicture = productService.getProductPicture(skuIds);
            for (Object map : productPicture) {
                Map picture = ObjectHandler.LinkedHashMapToMap(map);
                List imageUrl = (List) picture.get("image");
                if (imageUrl.size() > 0) {
                    pictureMap.put(picture.get("skuId"), ObjectHandler.LinkedHashMapToMap(imageUrl.get(0)).get("imageUrl"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pictureMap;
    }

    public String purchasePlanNum() {
        String id = "PP" + new SimpleDateFormat("yy").format(new java.util.Date());
        return id;
    }


    /**
     * 更新采购计划的状态，设置成已完成
     * 更新采购计划对应的采购订单号
     */
    @Override
    public void updateOrderId(String orderId, List<String> purchasePlanIdList) {
        for (String purchaseId : purchasePlanIdList) {
            purchasePlanInfoDao.updateOrderId(orderId, purchaseId);
        }
    }

    // 供应商交期关联参数处理
    public void getDeliveryMap(Map deliveryMap, List skuIdList, List<GetGeneralDelivery> getGeneralDelivery) {
        for (GetGeneralDelivery delivery : getGeneralDelivery) {
            if (delivery != null) {
                Map<String, Object> info = new HashMap();
                info.put("delivery", delivery.getGeneralDelivery());
                info.put("haulageDays", delivery.getHaulageDays());
                info.put("supplierId", delivery.getSupplierId());
                info.put("minimum", delivery.getMinimum());
                deliveryMap.put(delivery.getSkuId(), info);
                skuIdList.remove(skuIdList.indexOf(delivery.getSkuId()));
            }
        }
        if (skuIdList.size() > 0) {
            List<GetGeneralDelivery> maxTimeDelivery = supplierDateOfferDao.getRecentDelivery(skuIdList);
            for (GetGeneralDelivery maxTime : maxTimeDelivery) {
                if (maxTime == null) {
                    continue;
                }
                Map<String, Object> info = new HashMap();
                info.put("delivery", maxTime.getGeneralDelivery());
                info.put("haulageDays", maxTime.getHaulageDays());
                info.put("supplierId", maxTime.getSupplierId());
                info.put("minimum", maxTime.getMinimum());
                deliveryMap.put(maxTime.getSkuId(), info);
            }
        }
    }

    // 封装计划详情
    public void purchasePlanDealer(PurchasePlanInfoEntity planInfo, Date today, Map<String, Integer> estimateNumMap, Map<String, Integer> totalMap, String skuId, GetSetting setting, List<PurchasePlanDetailsEntity> details) {
        Integer availableTotal = totalMap.get(skuId);
        if (availableTotal == null) {
            availableTotal = 0;
        }
        Integer totalSales = 0;
        Integer maxSuggestPurchase = null;
        Date minSuggestDate = null;

        for (int m = 0; m < planInfo.getPlanDay(); m++) {
            try {
                PurchasePlanDetailsEntity planDetail = new PurchasePlanDetailsEntity();
                planDetail.setPlanDate(multipyDay(new Date(today.getTime()), m));
                planDetail.setId(Toolbox.randomUUID());
                planDetail.setUuid(planInfo.getUuid());
                planDetail.setExpectSales(estimateNumMap.get(Crypto.join(planInfo.getSkuId(), planDetail.getPlanDate().toString())));
                if (planDetail.getExpectSales() == null) {
                    planDetail.setExpectSales(0);
                }

                details.add(planDetail);
                totalSales += planDetail.getExpectSales();
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        planInfo.setSumSales(totalSales);

        for (int m = 0; m < details.size(); m++) {
            PurchasePlanDetailsEntity planDetail = details.get(m);
            planDetail.setAvgSales(planInfo.getSumSales() / (planInfo.getPlanDay() + 1));
            planDetail.setAvailableStock(availableTotal);
            planDetail.setAssignBalance(planDetail.getAvailableStock() - planDetail.getAvgSales());
            planDetail.setMinStock(planInfo.getPlanDay() * planDetail.getAvgSales());
            planDetail.setMinimum(planInfo.getMinimum());
            if (planDetail.getAssignBalance() < planDetail.getMinStock()) {
                int temp = planDetail.getMinStock() - planDetail.getAssignBalance() + planDetail.getAvgSales() * planInfo.getStockCycle();
                planDetail.setSuggestPurchase(temp > planDetail.getMinimum() ? temp : planDetail.getMinimum());
                planDetail.setLastPurchaseDate(multipyDay(planDetail.getPlanDate(), -(planInfo.getGeneralDelivery() + planInfo.getHaulageDays() + planInfo.getInternationalTransportDays())));
            }

            if (m == 0) {
                planInfo.setAvgSales(planDetail.getAvgSales());
                planInfo.setMinStock(planDetail.getMinStock());
                planInfo.setAvailableStock(availableTotal);
            }

            // 建议数量和建议日期
            if (maxSuggestPurchase == null) {
                maxSuggestPurchase = planDetail.getSuggestPurchase();
                if (maxSuggestPurchase != null) {
                    planInfo.setStartOutStock(planDetail.getPlanDate());
                }
            } else if (maxSuggestPurchase < planDetail.getSuggestPurchase()) {
                maxSuggestPurchase = planDetail.getSuggestPurchase();
            }
            if (minSuggestDate == null) {
                minSuggestDate = planDetail.getLastPurchaseDate();
            } else if (minSuggestDate.getTime() > planDetail.getLastPurchaseDate().getTime()) {
                minSuggestDate = planDetail.getLastPurchaseDate();
            }

            availableTotal -= planDetail.getAvgSales();
        }

        planInfo.setRecommendPurchase(maxSuggestPurchase);
        planInfo.setEndSalesDate(multipyDay(today, planInfo.getPlanDay()));
        planInfo.setSumSales(totalSales);
        planInfo.setSuggestCount(maxSuggestPurchase);
        planInfo.setSuggestDate(minSuggestDate);
        planInfo.setStatusCd(4);
        if (planInfo.getSuggestDate() != null) {
            planInfo.setExpectInWarehouse(multipyDay(planInfo.getSuggestDate(), planInfo.getGeneralDelivery() + planInfo.getHaulageDays()));
            if (planInfo.getSuggestDate().getTime() <= multipyDay(today, setting.getPurchaseWarmDay()).getTime()) {
                planInfo.setStatusCd(1);
            }
        }
    }

    // 封装供应商交期数据
    public void deliveryDealer(PurchasePlanInfoEntity planInfo, Map<String, Map<String, Object>> deliveryMap) {
        String skuId = planInfo.getSkuId();
        if (deliveryMap.get(skuId) == null || deliveryMap.get(skuId).get("delivery") == null) {
            planInfo.setGeneralDelivery(0);
        } else {
            planInfo.setGeneralDelivery((Integer) deliveryMap.get(skuId).get("delivery"));
        }
        if (deliveryMap.get(skuId) == null || deliveryMap.get(skuId).get("haulageDays") == null) {
            planInfo.setHaulageDays(0);
        } else {
            planInfo.setHaulageDays((Integer) deliveryMap.get(skuId).get("haulageDays"));
        }
        planInfo.setSupplierId(deliveryMap.get(skuId) == null ? null : (String) deliveryMap.get(skuId).get("supplierId"));
        if (deliveryMap.get(skuId) == null || deliveryMap.get(skuId).get("minimum") == null) {
            planInfo.setMinimum(0);
        } else {
            planInfo.setMinimum((Integer) deliveryMap.get(skuId).get("minimum"));
        }
    }
}
