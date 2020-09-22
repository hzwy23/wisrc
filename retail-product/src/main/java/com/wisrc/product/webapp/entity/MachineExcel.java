package com.wisrc.product.webapp.entity;

import lombok.Data;

@Data
public class MachineExcel {
    private String skuId;
    private String skuNameZh;
    private String dependencySkuId;
    private String dependencySkuName;
    private Integer quantity;
    private String typeDesc;
}
