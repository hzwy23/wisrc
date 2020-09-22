package com.wisrc.shipment.webapp.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class EarlyWarningLevelAttrEntity {
    private String earlyWarningLevelCd;
    private String earlyWarningLevelDesc;
    private Integer internalProcessingDays;
    private Integer headTransportationDays;
    private Integer signedIncomeWarehouseDays;
    private Integer agingDays;
    private Integer enableFlag;
    private Timestamp createTime;
}

