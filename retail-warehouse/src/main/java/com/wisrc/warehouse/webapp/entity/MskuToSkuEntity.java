package com.wisrc.warehouse.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class MskuToSkuEntity {
    @ApiModelProperty(value = "时间")
    private String dataDt;
    @ApiModelProperty(value = "库存SKU")
    private String skuId;
    @ApiModelProperty(value = "海外仓在途FBA退仓数量")
    private int fbaReturnQty;
    @ApiModelProperty(value = "FBA仓在途数量")
    private int fbaTransportQty;
    @ApiModelProperty(value = "FBA仓在仓数量")
    private int fbaStockQty;
    @ApiModelProperty(value = "MSKU编号")
    private String mskuId;
    @ApiModelProperty(value = "fnSkuId")
    private String fnSkuId;
    @ApiModelProperty(value = "店铺")
    private String shopName;

    public String getDataDt() {
        return dataDt;
    }

    public void setDataDt(String dataDt) {
        this.dataDt = dataDt;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public int getFbaReturnQty() {
        return fbaReturnQty;
    }

    public void setFbaReturnQty(int fbaReturnQty) {
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
