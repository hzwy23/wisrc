package com.wisrc.replenishment.webapp.service.impl;

import com.wisrc.replenishment.webapp.dao.InspectionPlanDao;
import com.wisrc.replenishment.webapp.dto.logisticsPlan.GetLogisticsPlanPageReturnDto;
import com.wisrc.replenishment.webapp.dto.logisticsPlan.GetMskuPageDto;
import com.wisrc.replenishment.webapp.dto.logisticsPlan.LogisticsPlanByMskuIdDto;
import com.wisrc.replenishment.webapp.dto.logisticsPlan.LogisticsPlanPageDto;
import com.wisrc.replenishment.webapp.dto.msku.MskuPlanPageDTO;
import com.wisrc.replenishment.webapp.entity.InspectionPlanEntity;
import com.wisrc.replenishment.webapp.query.DeleteInspectionPlan;
import com.wisrc.replenishment.webapp.query.GetInspectionPlanByPlanIdsQuery;
import com.wisrc.replenishment.webapp.service.LogisticsPlanService;
import com.wisrc.replenishment.webapp.service.ReplenishmentMskuService;
import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.utils.Time;
import com.wisrc.replenishment.webapp.vo.logisticsPlan.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LogisticsPlanServiceImpl implements LogisticsPlanService {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private ReplenishmentMskuService replenishmentMskuService;
    @Autowired
    private InspectionPlanDao inspectionPlanDao;

    @Override
    public Result getLogisticsPlanPage(LogisticsPlanPageVo logisticsPlanPageVo, String userId) {
        LogisticsPlanPageDto result;
        List<String> commodityId = null;

        // 获取商品信息
        GetMskuPageDto getMskuPageDto = new GetMskuPageDto();
        BeanUtils.copyProperties(logisticsPlanPageVo, getMskuPageDto);
        getMskuPageDto.setDoesDelete(1);
        try {
            result = replenishmentMskuService.getMskuPage(getMskuPageDto, userId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure(400, "商品模块发生错误，数据无法获取" + e.getMessage(), "");
        }

        if (logisticsPlanPageVo.getSalesLastEndTime() != null || logisticsPlanPageVo.getSalesLastStartTime() != null) {
            // 筛选出符合计划截止日期区间的msku
            try {
                commodityId = inspectionPlanDao.mskuInSalesEndTime(logisticsPlanPageVo.getSalesLastEndTime(), logisticsPlanPageVo.getSalesLastStartTime());
                result.setTotal(commodityId.size());
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }
        }

        // 根据页面商品获取计划信息并去除不符合搜索条件的商品
        List<GetLogisticsPlanPageReturnDto> logisticsPlanPage = result.getLogisticsPlanList();
        List<String> planIds = new ArrayList<>();
        for (int m = 0; m < logisticsPlanPage.size(); m++) {
            GetLogisticsPlanPageReturnDto getLogisticsPlan = logisticsPlanPage.get(m);
            if (commodityId != null) {
                if (commodityId.indexOf(getLogisticsPlan.getId()) == -1) {
                    logisticsPlanPage.remove(m);
                    m--;
                    continue;
                }
            }
            planIds.add(getLogisticsPlan.getId());
        }
        result.setPages((int) Math.floor(result.getTotal() / getMskuPageDto.getPageSize()) + 1);

        List<InspectionPlanEntity> inspectionPlanDetails;
        Map<String, InspectionPlanEntity> planDetailMap = new HashMap<>();

        try {
            GetInspectionPlanByPlanIdsQuery inspectionPlanQuery = new GetInspectionPlanByPlanIdsQuery();
            inspectionPlanQuery.setCommodityIds(planIds);
            if (logisticsPlanPageVo.getSalesLastEndTime() != null || logisticsPlanPageVo.getSalesLastStartTime() != null) {
                inspectionPlanQuery.setDeliveryPlanStartDate(logisticsPlanPageVo.getSalesLastStartTime());
                inspectionPlanQuery.setDeliveryPlanEndDate(logisticsPlanPageVo.getSalesLastEndTime());
            }
            inspectionPlanDetails = inspectionPlanDao.getInspectionPlanByPlanIds(inspectionPlanQuery);
            for (InspectionPlanEntity inspectionPlanDetail : inspectionPlanDetails) {
                planDetailMap.put(inspectionPlanDetail.getCommodityId(), inspectionPlanDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 封装计划信息
        for (GetLogisticsPlanPageReturnDto logisticsPlan : logisticsPlanPage) {
            InspectionPlanEntity inspectionPlanDetail = planDetailMap.get(logisticsPlan.getId());
            if (inspectionPlanDetail != null) {
                logisticsPlan.setSalesEndTime(inspectionPlanDetail.getDeliveryPlanDate());
                logisticsPlan.setDeliveryPlanQuantity(inspectionPlanDetail.getDeliveryPlanQuantity());
            }
        }

        return Result.success(result);
    }

    @Override
    public Result saveLogisticsPlan(MskuLogisticsPlanSaveVo mskuLogisticsPlanSaveVo, String userId, String commodityId) {
        List<InspectionPlanEntity> planList = planVoToEntity(mskuLogisticsPlanSaveVo, userId, commodityId);

        for (InspectionPlanEntity planEntity : planList) {
            try {
                inspectionPlanDao.saveInspectionPlan(planEntity);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }
        }

        return Result.success();
    }

    @Override
    public Result editLogisticsPlan(MskuLogisticsPlanEditVo mskuLogisticsPlanEditVo, String userId, String logisticsPlanId) {
        InspectionPlanEntity planEntity = new InspectionPlanEntity();
        BeanUtils.copyProperties(mskuLogisticsPlanEditVo, planEntity);
        planEntity.setLogisticsPlanId(logisticsPlanId);
        planEntity.setDeliveryPlanDate(new java.sql.Date(mskuLogisticsPlanEditVo.getDeliveryPlanDate().getTime()));
        planEntity.setModifyUser(userId);
        planEntity.setModifyTime(Time.getCurrentTimestamp());

        try {
            inspectionPlanDao.editInspectionPlan(planEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        return Result.success();
    }

    // 根据输入数据封装物流计划对象
    public List<InspectionPlanEntity> planVoToEntity(MskuLogisticsPlanVo mskuPlansVo, String userId, String commodityId) {
        List<? extends LogisticsPlanVo> plansVo = (List<? extends LogisticsPlanVo>) mskuPlansVo.getPlanBatch();
        List<InspectionPlanEntity> plan = new ArrayList<>();

        for (LogisticsPlanVo planVo : plansVo) {
            InspectionPlanEntity planEntity = new InspectionPlanEntity();
            BeanUtils.copyProperties(planVo, planEntity);
            planEntity.setCommodityId(commodityId);
            planEntity.setCreateUser(userId);
            planEntity.setCreateTime(Time.getCurrentTimestamp());
            planEntity.setModifyUser(userId);
            planEntity.setModifyTime(Time.getCurrentTimestamp());
            planEntity.setDeleteStatus(0);

            try {
                planEntity.setLogisticsPlanId(((LogisticsPlanEditVo) planVo).getLogisticsPlanId());
            } catch (ClassCastException e) {
                planEntity.setLogisticsPlanId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
            }

            plan.add(planEntity);
        }

        return plan;
    }

    @Override
    public Result logisticsPlanByMskuId(String commodityId) {
        List<InspectionPlanEntity> inspectionPlanResult;
        List<LogisticsPlanByMskuIdDto> result = new ArrayList<>();

        try {
            inspectionPlanResult = inspectionPlanDao.inspectionPlanByMskuId(commodityId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

        for (InspectionPlanEntity inspectionPlanEntity : inspectionPlanResult) {
            LogisticsPlanByMskuIdDto planDto = new LogisticsPlanByMskuIdDto();
            BeanUtils.copyProperties(inspectionPlanEntity, planDto);
            planDto.setDeliveryPlanDate(sdf.format(inspectionPlanEntity.getDeliveryPlanDate()));
            planDto.setSalesStartTime(sdf.format(inspectionPlanEntity.getSalesStartTime()));
            planDto.setSalesEndTime(sdf.format(inspectionPlanEntity.getSalesEndTime()));

            result.add(planDto);
        }

        return Result.success(result);
    }

    @Override
    public Result deleteLogisticsPlan(String logisticsPlanId, String userId) {
        DeleteInspectionPlan deleteInspectionPlan = new DeleteInspectionPlan();
        deleteInspectionPlan.setLogisticsPlanId(logisticsPlanId);
        deleteInspectionPlan.setModifyUser(userId);
        deleteInspectionPlan.setModifyTime(Time.getCurrentTimestamp());

        try {
            inspectionPlanDao.deleteInspectionPlan(deleteInspectionPlan);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
        return Result.success();
    }

    @Override
    public Result getSalesDemandQuantity(GetSalesDemandQuantityVo getSalesDemandQuantityVo, String commodityId) {
        Integer salesDemandQuantity = 0;
        List<MskuPlanPageDTO> salesPlan;
        try {
            salesPlan = replenishmentMskuService.getSalesPlanData(commodityId);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }

//        try {
//            for (MskuPlanPageDTO mskuSalePlan : salesPlan) {
//                Date salesStartDate = sdf.parse(mskuSalePlan.getStartDate());
//                Date salesEndDate = sdf.parse(mskuSalePlan.getExpiryDate());
//                Date startDate;
//                Date endDate;
//
//                if (!(salesStartDate.getTime() > getSalesDemandQuantityVo.getSalesEndTime().getTime() ||
//                        salesEndDate.getTime() < getSalesDemandQuantityVo.getSalesStartTime().getTime())) {
//                    if (salesStartDate.getTime() < getSalesDemandQuantityVo.getSalesStartTime().getTime()) {
//                        startDate = getSalesDemandQuantityVo.getSalesStartTime();
//                    }else {
//                        startDate = salesStartDate;
//                    }
//                    if (salesEndDate.getTime() < getSalesDemandQuantityVo.getSalesEndTime().getTime()) {
//                        endDate = salesEndDate;
//                    }else {
//                        endDate = getSalesDemandQuantityVo.getSalesEndTime();
//                    }
//                    int day = daysBetween(startDate, endDate);
//                    salesDemandQuantity = day * mskuSalePlan.getExpectedDailySales();
//                }
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return Result.failure();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.failure();
//        }

        return Result.success(salesDemandQuantity);
    }

    @Override
    public Result getSalesLaseEndTime(String commodityId) {
        String endTime;

        try {
            endTime = sdf.format(inspectionPlanDao.getSalesLaseEndTime(commodityId));
        } catch (Exception e) {
            return new Result(200, "", null);
        }

        return Result.success(endTime);
    }

    @Override
    public Boolean checkSalesDate(MskuLogisticsPlanVo mskuLogisticsPlanVo, String commodityId) {
        List<LogisticsPlanVo> planBatch = (List<LogisticsPlanVo>) mskuLogisticsPlanVo.getPlanBatch();
        List<InspectionPlanEntity> planSaved;

        try {
            planSaved = inspectionPlanDao.inspectionPlanByMskuId(commodityId);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }

        List<List<Date>> dateTime = new ArrayList();
        for (InspectionPlanEntity plan : planSaved) {
            List dateList = new ArrayList();
            dateList.add(plan.getSalesStartTime());
            dateList.add(plan.getSalesEndTime());
            dateTime.add(dateList);
        }

        for (LogisticsPlanVo logisticsPlanVo : planBatch) {
            if (logisticsPlanVo.getSalesStartTime().getTime() > logisticsPlanVo.getSalesEndTime().getTime()) {
                return true;
            }
            for (List<Date> dates : dateTime) {
                if (!(logisticsPlanVo.getSalesEndTime().getTime() > dates.get(0).getTime() || logisticsPlanVo.getSalesStartTime().getTime() < dates.get(1).getTime())) {
                    return true;
                }
            }
            List dateList = new ArrayList();
            dateList.add(logisticsPlanVo.getSalesStartTime());
            dateList.add(logisticsPlanVo.getSalesEndTime());
            dateTime.add(dateList);
        }

        return false;
    }

    public int daysBetween(Date smdate, Date bdate) throws ParseException {
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }
}
