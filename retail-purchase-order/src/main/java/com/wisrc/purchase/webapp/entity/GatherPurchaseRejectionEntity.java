package com.wisrc.purchase.webapp.entity;

import lombok.Data;

import java.util.Date;

@Data
public class GatherPurchaseRejectionEntity {
    //PurchaseRejectionDetailsEntity
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
    private Integer deleteStatus;

    //PurchaseRejectionInfoEntity
    private Date rejectionDate;
    private Date rejectionDateStart;
    private Date rejectionDateEnd;
    private String handleUser;
    private String inspectionId;
    private String supplierCd;
    private String supplierDeliveryNum;
    private String remark;
    private String orderId;
}
