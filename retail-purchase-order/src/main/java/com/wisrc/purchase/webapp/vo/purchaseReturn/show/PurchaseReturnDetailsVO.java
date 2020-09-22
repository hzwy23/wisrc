package com.wisrc.purchase.webapp.vo.purchaseReturn.show;

import lombok.Data;

@Data
public class PurchaseReturnDetailsVO {
    private String uuid;
    private String returnBill;
    private String skuId;
    private String skuNameZh;
    private Integer returnQuantity;
    private Integer spareQuantity;
    private String batchNumber;
    private Double unitPriceWithoutTax;
    private Double amountWithoutTax;
    private Double taxRate;
    private Double unitPriceWithTax;
    private Double amountWithTax;
}
