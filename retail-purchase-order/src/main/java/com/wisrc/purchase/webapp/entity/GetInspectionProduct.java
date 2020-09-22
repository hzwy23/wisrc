package com.wisrc.purchase.webapp.entity;

import lombok.Data;

@Data
public class GetInspectionProduct extends ArrivalProductDetailsInfoEntity {
    private String arrivalId;
    private String supplierId;
    private String purchaseOrderId;
    private Integer status_cd;
}
