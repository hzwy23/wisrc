package com.wisrc.product.webapp.entity;

import lombok.Data;


//装箱信息
@Data
public class ProductPackingInfoEntity {
    private String skuId;
    private double packLength;
    private double packHeight;
    private double packWidth;
    private int packQuantity;
    private double packWeight;

}
