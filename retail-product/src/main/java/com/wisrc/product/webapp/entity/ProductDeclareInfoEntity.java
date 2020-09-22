package com.wisrc.product.webapp.entity;

import lombok.Data;

/**
 * 产品申报信息实体类
 */
@Data
public class ProductDeclareInfoEntity extends BaseEntity {
    private String skuId;
    private String customsNumber;
    private double taxRebatePoint;
    private String issuingOffice;
    private String declareNameZh;
    private String declareNameEn;
    private double declarePrice;
    private double singleWeight;
    private String declarationElements;
    private String ticketName;


    private String originPlace;
    private String materials;
    private String typicalUse;
    private String brands;
    private String model;

}
