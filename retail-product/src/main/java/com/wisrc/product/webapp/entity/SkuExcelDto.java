package com.wisrc.product.webapp.entity;

import lombok.Data;

@Data
public class SkuExcelDto {
    private String skuId;
    private String skuNameZh;
    private Double purchaseReferencePrice;
    private Double costPrice;
    private String machineFlag;
    private String packingFlag;
    private String classify;
    private String skuNameEn;
    private Integer safetyStockDays;
    private Integer internationalTransportDays;
    private String description;
    private String customsNumber;
    private Double taxRebatePoint;
    private String issuingOffice;
    private String declareNameZh;
    private String declareNameEn;
    private Double declarePrice;
    private String originPlace;
    private String materials;
    private String typicalUse;
    private String brands;
    private String model;
    private String declarationElements;
}
