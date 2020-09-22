package com.wisrc.purchase.webapp.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class GatherPurchaseReturnEntity {
    private String returnBill;
    private Date createDate;
    private String supplierId;
    private String employeeId;
    private String warehouseId;
    private String orderId;
    private String remark;
    private Integer deleteStatus;
    private String createUser;
    private Timestamp createTime;
    private String modifyUser;
    private Timestamp modifyTime;
    private Integer statusCd;
    private String statusModifyTime;
    private String packWarehouseId;

    private Date createDateStart;
    private Date createDateEnd;

    private String uuid;
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
