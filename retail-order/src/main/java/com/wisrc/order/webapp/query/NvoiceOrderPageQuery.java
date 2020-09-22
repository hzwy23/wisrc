package com.wisrc.order.webapp.query;

import lombok.Data;

import java.util.List;

@Data
public class NvoiceOrderPageQuery {
    private String invoiceNumber;
    private Integer statusCd;
    private String wmsWaveNumber;
    private String orderId;
    private String originalOrderId;
    private List mskuIds;
}
