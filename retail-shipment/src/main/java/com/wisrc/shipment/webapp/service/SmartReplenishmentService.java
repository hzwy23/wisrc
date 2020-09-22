package com.wisrc.shipment.webapp.service;

import com.wisrc.shipment.webapp.utils.Result;

public interface SmartReplenishmentService {
    Result addWarning();

    Result updateDefaultSecurityAlertDaysInfo(Integer defaultSecurityStockDays, Integer earlyWarningDays);

    Result getWarning(String shopId, String earlyWarningLevelDesc, Integer salesStatusCd, String keyWords, Integer pageNum, Integer pageSize);


    Result getWaringLevel();

    Result getDefaultSecurityAlertDaysInfo();
}
