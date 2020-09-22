package com.wisrc.crawler.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class MskuInfoEnity {
    private String msku;
    private String fnsku;
    private String asin;
    @ApiModelProperty(value = "msku英文名")
    private String mskuCnName;
    @ApiModelProperty(value = "售价")
    private Double SalePrice;
    @ApiModelProperty(value = "配送方式")
    private String deliveryType;

    public String getMsku() {
        return msku;
    }

    public void setMsku(String msku) {
        this.msku = msku;
    }

    public String getFnsku() {
        return fnsku;
    }

    public void setFnsku(String fnsku) {
        this.fnsku = fnsku;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getMskuCnName() {
        return mskuCnName;
    }

    public void setMskuCnName(String mskuCnName) {
        this.mskuCnName = mskuCnName;
    }

    public Double getSalePrice() {
        return SalePrice;
    }

    public void setSalePrice(Double salePrice) {
        SalePrice = salePrice;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }
}
