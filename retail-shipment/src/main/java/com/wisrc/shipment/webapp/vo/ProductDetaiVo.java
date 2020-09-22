package com.wisrc.shipment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class ProductDetaiVo {
    private String commodityId;
    private String picture;
    private String mskuId;
    private String skuId;
    private Integer applyReturnNum;
    private String fnsku;
    private String asin;
    private String mskuName;
    private String productName;
    private String returnApplyId;
    private Double salePrice;
    private String uuid;
    @ApiModelProperty(value = "出库数", hidden = true)
    private Integer outWarehouseNum;
    @ApiModelProperty(value = "签收数", hidden = true)
    private Integer signNum;

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Integer getApplyReturnNum() {
        return applyReturnNum;
    }

    public void setApplyReturnNum(Integer applyReturnNum) {
        this.applyReturnNum = applyReturnNum;
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

    public String getReturnApplyId() {
        return returnApplyId;
    }

    public void setReturnApplyId(String returnApplyId) {
        this.returnApplyId = returnApplyId;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public String getMskuName() {
        return mskuName;
    }

    public void setMskuName(String mskuName) {
        this.mskuName = mskuName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getOutWarehouseNum() {
        return outWarehouseNum;
    }

    public void setOutWarehouseNum(Integer outWarehouseNum) {
        this.outWarehouseNum = outWarehouseNum;
    }

    public Integer getSignNum() {
        return signNum;
    }

    public void setSignNum(Integer signNum) {
        this.signNum = signNum;
    }
}
