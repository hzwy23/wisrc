package com.wisrc.purchase.webapp.entity;

import lombok.Data;

@Data
public class PurchaseRejectionDetailsEntity {
    private String uuid;
    private String rejectionId;
    private String skuId;
    private Integer rejectQuantity;
    private Integer spareQuantity;
    private String batchNumber;
    private Double unitPriceWithoutTax;
    private Double amountWithoutTax;
    private Double taxRate;
    private Double unitPriceWithTax;
    private Double amountWithTax;
}
