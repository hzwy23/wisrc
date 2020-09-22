package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class MskuToSkuEntity {
    @ApiModelProperty(value = "库存SKU")
    private String storeSku;
    @ApiModelProperty(value = "海外仓在途FBA退仓数量")
    private String fbaReturnQty;
    @ApiModelProperty(value = "FBA仓在途数量")
    private int fbaTransportQty;
    @ApiModelProperty(value = "FBA仓在仓数量")
    private int fbaStockQty;
    @ApiModelProperty(value = "MSKU编号")
    private String msku;
    @ApiModelProperty(value = "店铺")
    private String shopName;

    public String getStoreSku() {
        return storeSku;
    }

    public void setStoreSku(String storeSku) {
        this.storeSku = storeSku;
    }

    public String getFbaReturnQty() {
        return fbaReturnQty;
    }

    public void setFbaReturnQty(String fbaReturnQty) {
        this.fbaReturnQty = fbaReturnQty;
    }

    public int getFbaTransportQty() {
        return fbaTransportQty;
    }

    public void setFbaTransportQty(int fbaTransportQty) {
        this.fbaTransportQty = fbaTransportQty;
    }

    public int getFbaStockQty() {
        return fbaStockQty;
    }

    public void setFbaStockQty(int fbaStockQty) {
        this.fbaStockQty = fbaStockQty;
    }

    public String getMsku() {
        return msku;
    }

    public void setMsku(String msku) {
        this.msku = msku;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
