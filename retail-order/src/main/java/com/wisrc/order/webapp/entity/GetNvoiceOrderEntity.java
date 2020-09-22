package com.wisrc.order.webapp.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GetNvoiceOrderEntity {
    private String invoiceNumber;
    private String statusDesc;
    private String wmsWaveNumber;
    private String orderId;
    private String originalOrderId;
    private BigDecimal totalWeight;
    private String offerId;
    private String logisticsId;
    private String logisticsCost;
    private List<OrderCommodityInfoEntity> saleOrderCommodity;
}
