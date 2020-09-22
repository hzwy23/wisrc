package com.wisrc.warehouse.webapp.entity;

import lombok.Data;

@Data
public class ReportLossStatementEntity {
    private String reportLossStatementId;
    private Integer labelFlag;
    private String reportLossReason;
    private String annexAddress;
    private Integer statusCd;
    private String applyPersonId;
    private String createTime;
    private String warehouseId;
    private String reviewTime;
    private String reviewPersonId;
    private String cancelReason;
    private String disagreeReason;
}
