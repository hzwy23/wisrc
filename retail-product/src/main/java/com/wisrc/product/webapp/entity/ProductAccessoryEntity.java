package com.wisrc.product.webapp.entity;

import lombok.Data;

@Data
public class ProductAccessoryEntity {
    private String uuid;
    private String skuId;
    private Integer accessoryCd;
    private String accessoryText;
    private String accessoryDesc;
    private Integer typeCd;
}
