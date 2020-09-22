package com.wisrc.warehouse.webapp.vo;

public class MskuFbaVO {
    private String skuId;
    private String mskuId;
    private String fnSkuId;
    private String shopId;
    private int fbaTransportQty;
    private int fbaStockQty;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
    }

    public String getFnSkuId() {
        return fnSkuId;
    }

    public void setFnSkuId(String fnSkuId) {
        this.fnSkuId = fnSkuId;
    }

    public int getFbaTransportQty() {
        return fbaTransportQty;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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
}
