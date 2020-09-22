package com.wisrc.purchase.webapp.entity;

import lombok.Data;

@Data
public class PurchaseReturnDetailsEntity {
    private String uuid;
    private String returnBill;
    private String skuId;
    private Integer returnQuantity;
    private Integer spareQuantity;
    private String batchNumber;
    private Double unitPriceWithoutTax;
    private Double amountWithoutTax;
    private Double taxRate;
    private Double unitPriceWithTax;
    private Double amountWithTax;
}
