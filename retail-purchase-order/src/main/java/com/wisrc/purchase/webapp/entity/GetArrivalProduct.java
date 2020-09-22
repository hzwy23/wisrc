package com.wisrc.purchase.webapp.entity;

import lombok.Data;

@Data
public class GetArrivalProduct extends ArrivalProductDetailsInfoEntity {
    private String arrivalId;
    private String supplierId;
    private String purchaseOrderId;
    private String statusDesc;
}
