package com.wisrc.replenishment.webapp.service;

import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.vo.logisticsPlan.*;

public interface LogisticsPlanService {
    Result getLogisticsPlanPage(LogisticsPlanPageVo logisticsPlanPageVo, String userId);

    Result saveLogisticsPlan(MskuLogisticsPlanSaveVo mskuLogisticsPlanSaveVo, String userId, String commodityId);

    Result editLogisticsPlan(MskuLogisticsPlanEditVo mskuLogisticsPlanEditVo, String userId, String logisticsPlanId);

    Result logisticsPlanByMskuId(String commodityId);

    Result deleteLogisticsPlan(String logisticsPlanId, String userId);

    Result getSalesDemandQuantity(GetSalesDemandQuantityVo getSalesDemandQuantityVo, String commodityId);

    Result getSalesLaseEndTime(String commodityId);

    Boolean checkSalesDate(MskuLogisticsPlanVo mskuLogisticsPlanVo, String commodityId);
}
