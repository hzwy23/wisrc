package com.wisrc.purchase.webapp.vo.purchaseRejection;

import lombok.Data;

@Data
public class PurchaseRejectionDetailsVO {
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
    private String skuNameZh;
}
