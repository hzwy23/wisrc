package com.wisrc.replenishment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class CustomsProductListVO {
    @ApiModelProperty(value = "产品信息唯一ID")
    private String replenishmentCommodityId;
    @ApiModelProperty("当交运单是调拨单生成的时候传skuId")
    private String skuId;
    @ApiModelProperty(value = "毛重", hidden = true)
    private double grossWeight;
    @ApiModelProperty(value = "净重", hidden = true)
    private double netWeight;
    @ApiModelProperty(value = "申报单价")
    private double declareUnitPrice;
    @ApiModelProperty(value = "单位")
    private String mskuUnitCd;
    @ApiModelProperty(value = "申报要素")
    private String declarationElements;
    @ApiModelProperty(value = "小计", hidden = true)
    private double declareSubtotal;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getReplenishmentCommodityId() {
        return replenishmentCommodityId;
    }

    public void setReplenishmentCommodityId(String replenishmentCommodityId) {
        this.replenishmentCommodityId = replenishmentCommodityId;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    public double getDeclareUnitPrice() {
        return declareUnitPrice;
    }

    public void setDeclareUnitPrice(double declareUnitPrice) {
        this.declareUnitPrice = declareUnitPrice;
    }

    public String getMskuUnitCd() {
        return mskuUnitCd;
    }

    public void setMskuUnitCd(String mskuUnitCd) {
        this.mskuUnitCd = mskuUnitCd;
    }

    public String getDeclarationElements() {
        return declarationElements;
    }

    public void setDeclarationElements(String declarationElements) {
        this.declarationElements = declarationElements;
    }

    public double getDeclareSubtotal() {
        return declareSubtotal;
    }

    public void setDeclareSubtotal(double declareSubtotal) {
        this.declareSubtotal = declareSubtotal;
    }
}
