package com.wisrc.warehouse.webapp.entity;

import lombok.Data;

@Data
public class DefaultSecurityAlertDaysInfoEntity {
    private String id;
    private Integer earlyWarningDays;
    private Integer defaultSecurityStockDays;
}
