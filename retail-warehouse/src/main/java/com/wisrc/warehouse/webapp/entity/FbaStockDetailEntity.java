package com.wisrc.warehouse.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class FbaStockDetailEntity {
    @ApiModelProperty(value = "fnSkuId")
    private String fnSkuId;
    @ApiModelProperty(value = "店铺Id")
    private String shopId;
    @ApiModelProperty(value = "店铺名称")
    private String shopName;
    @ApiModelProperty(value = "mskuId")
    private String mskuId;
    @ApiModelProperty(value = "库存总量")
    private int sumStock;
    @ApiModelProperty(value = "不可用库存")
    private int unableQuantity;
    @ApiModelProperty(value = "可用库存")
    private int enableStockNum;

    public String getFnSkuId() {
        return fnSkuId;
    }

    public void setFnSkuId(String fnSkuId) {
        this.fnSkuId = fnSkuId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
    }

    public int getSumStock() {
        return sumStock;
    }

    public void setSumStock(int sumStock) {
        this.sumStock = sumStock;
    }

    public int getUnableQuantity() {
        return unableQuantity;
    }

    public void setUnableQuantity(int unableQuantity) {
        this.unableQuantity = unableQuantity;
    }

    public int getEnableStockNum() {
        return enableStockNum;
    }

    public void setEnableStockNum(int enableStockNum) {
        this.enableStockNum = enableStockNum;
    }
}
