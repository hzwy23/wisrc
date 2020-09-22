package com.wisrc.purchase.webapp.entity;

import lombok.Data;

import java.util.List;

@Data
public class PurchasePlanPage extends PurchasePlanInfoEntity {
    private String statusDesc;
    private String calculateTypeDesc;
    private List<String> remarks;
}
