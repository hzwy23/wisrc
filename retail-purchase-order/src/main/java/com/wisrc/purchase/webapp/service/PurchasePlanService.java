package com.wisrc.purchase.webapp.service;

import com.wisrc.purchase.webapp.utils.Result;
import com.wisrc.purchase.webapp.vo.purchasePlan.PlanTimeEditVo;
import com.wisrc.purchase.webapp.vo.purchasePlan.PurchasePlanPageVo;
import com.wisrc.purchase.webapp.vo.purchasePlan.PurchaseSettingEditVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface PurchasePlanService {
    void savePurchasePlan();

    Result purchasePlanPage(PurchasePlanPageVo purchasePlanPageVo);

    Result getPurchasePlan(String uuid);

    Result decidePlan(List<String> uuids);

    Result cancelPlan(List uuids);

    Result excelPlan(PurchasePlanPageVo purchasePlanPageVo, HttpServletResponse response, HttpServletRequest request);

    Result planStatusSelector();

    Result purchaseSetting();

    Result editPurchaseSetting(PurchaseSettingEditVo purchaseSettingEditVo);

    Result getPlanTime(String uuid);

    Result editPlanTime(String uuid, PlanTimeEditVo planTimeEditVo, String userId);

    Result saveRemark(String remark, String uuid, String userId);

    Result dayWeekSelector();

    Result weekAttrSelector();

    void updateOrderId(String orderId, List<String> purchasePlanIdList);
}
