package com.wisrc.order.webapp.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class NvoiceOrderEntity {
    private String invoiceNumber;
    private String statusDesc;
    private String wmsWaveNumber;
    private String orderId;
    private String originalOrderId;
    private Timestamp createTime;
    private BigDecimal totalWeight;
    private String offerId;
    private String logisticsId;
    private String logisticsCost;
}
