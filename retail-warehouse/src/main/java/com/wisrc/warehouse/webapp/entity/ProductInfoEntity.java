package com.wisrc.warehouse.webapp.entity;

import lombok.Data;

@Data
public class ProductInfoEntity {
    private String uuid;
    private String reportLossStatementId;
    private String skuId;
    private String fnSku;
    private Integer reportedLossAmount;
}
