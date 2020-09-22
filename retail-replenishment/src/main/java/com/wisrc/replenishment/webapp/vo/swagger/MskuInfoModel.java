package com.wisrc.replenishment.webapp.vo.swagger;

import io.swagger.annotations.ApiModelProperty;

public class MskuInfoModel {

    @ApiModelProperty(value = "msku编号", position = 1)
    private String msku;
    @ApiModelProperty(value = "库存msku编号", position = 2)
    private String storeSku;
    @ApiModelProperty(value = "msku名称", position = 3)
    private String mskuName;
    @ApiModelProperty(value = "库存msku名称", position = 4)
    private String storeName;
    @ApiModelProperty(value = "fnsku", position = 5)
    private String FnSKU;
    @ApiModelProperty(value = "asin", position = 6)
    private String ASIN;
    @ApiModelProperty(value = "产品特性", position = 7)
    private String prodFeatu;//产品特性，待定字段
    @ApiModelProperty(value = "销售状态", position = 8)
    private String salesStatus;
    @ApiModelProperty(value = "商品负责人", position = 9)
    private String manager;
    @ApiModelProperty(value = "商品图片", position = 10)
    private String picture;

    public String getMsku() {
        return msku;
    }

    public void setMsku(String msku) {
        this.msku = msku;
    }

    public String getStoreSku() {
        return storeSku;
    }

    public void setStoreSku(String storeSku) {
        this.storeSku = storeSku;
    }

    public String getMskuName() {
        return mskuName;
    }

    public void setMskuName(String mskuName) {
        this.mskuName = mskuName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getFnSKU() {
        return FnSKU;
    }

    public void setFnSKU(String fnSKU) {
        FnSKU = fnSKU;
    }

    public String getASIN() {
        return ASIN;
    }

    public void setASIN(String ASIN) {
        this.ASIN = ASIN;
    }

    public String getProdFeatu() {
        return prodFeatu;
    }

    public void setProdFeatu(String prodFeatu) {
        this.prodFeatu = prodFeatu;
    }

    public String getSalesStatus() {
        return salesStatus;
    }

    public void setSalesStatus(String salesStatus) {
        this.salesStatus = salesStatus;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
