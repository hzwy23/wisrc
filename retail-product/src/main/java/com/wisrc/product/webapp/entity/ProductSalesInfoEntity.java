package com.wisrc.product.webapp.entity;


import lombok.Data;


/**
 * 产品定义信息实体类
 */
@Data
public class ProductSalesInfoEntity {
    private String skuId;
    private Integer salesStatusCd;
    private Integer safetyStockDays;
    private Integer internationalTransportDays;

}
