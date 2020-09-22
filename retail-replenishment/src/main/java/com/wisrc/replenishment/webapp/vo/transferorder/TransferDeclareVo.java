package com.wisrc.replenishment.webapp.vo.transferorder;

import io.swagger.annotations.ApiModelProperty;

public class TransferDeclareVo extends TransferOrderDetailsVo {
    @ApiModelProperty("海关编码")
    private String customsNumber;
    @ApiModelProperty("申报英文名")
    private String declareNameEn;
    @ApiModelProperty("申报中文名")
    private String declareNameZh;
    @ApiModelProperty("申报因素")
    private String declarationElements;
    @ApiModelProperty("申报单价")
    private double declareUnitPrice;
    @ApiModelProperty("产品单位")
    private String skuUnitCd;

    public String getCustomsNumber() {
        return customsNumber;
    }

    public void setCustomsNumber(String customsNumber) {
        this.customsNumber = customsNumber;
    }

    public String getDeclareNameEn() {
        return declareNameEn;
    }

    public void setDeclareNameEn(String declareNameEn) {
        this.declareNameEn = declareNameEn;
    }

    public String getDeclareNameZh() {
        return declareNameZh;
    }

    public void setDeclareNameZh(String declareNameZh) {
        this.declareNameZh = declareNameZh;
    }

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
