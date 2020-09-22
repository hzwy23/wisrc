package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class TransferDeclareEntity {
    @ApiModelProperty("申报因素")
    private String declarationElements;
    @ApiModelProperty("申报单价")
    private double declareUnitPrice;
    @ApiModelProperty("产品单位")
    private String skuUnitCd;

    public String getDeclarationElements() {
        return declarationElements;
    }

    public void setDeclarationElements(String declarationElements) {
        this.declarationElements = declarationElements;
    }

    public double getDeclareUnitPrice() {
        return declareUnitPrice;
    }

    public void setDeclareUnitPrice(double declareUnitPrice) {
        this.declareUnitPrice = declareUnitPrice;
    }

    public String getSkuUnitCd() {
        return skuUnitCd;
    }

    public void setSkuUnitCd(String skuUnitCd) {
        this.skuUnitCd = skuUnitCd;
    }
}
