package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 商品结存明细表
 */
public class WsRmpWarehouseStockSumEntity {
    @ApiModelProperty(value = "库存SKU")
    private String storeSku;
    @ApiModelProperty(value = "库存SKU/产品名称")
    private String cnName;
    @ApiModelProperty(value = "本地仓在途生产中总数量")
    private int productQty;
    @ApiModelProperty(value = "本地仓在途运输中总数量")
    private int transportQty;
    @ApiModelProperty(value = "本地仓在仓番禺仓总数量")
    private int panyuStockQty;
    @ApiModelProperty(value = "本地仓在仓虚拟仓总数量")
    private int virtualStockQty;
    @ApiModelProperty(value = "海外仓在途国内调拨数量")
    private int overseasTransportQty;
    /*@ApiModelProperty(value = "海外仓在途FBA退仓数量")
    private int fbaReturnQty;*/
    @ApiModelProperty(value = "海外仓在仓数量")
    private int overseasStockQty;
    /*@ApiModelProperty(value = "FBA仓在途数量")
    private int fbaTransportQty;
    @ApiModelProperty(value = "FBA仓在仓数量")
    private int fbaStockQty;
    @ApiModelProperty(value = "MSKU编号")
    private String msku;
    @ApiModelProperty(value = "店铺")
    private String shopName;*/
    @ApiModelProperty(value = "合计")
    private String totalQty;
    @ApiModelProperty(value = "sku对应的MSKU")
    private List<MskuToSkuEntity> entityList;

    public List<MskuToSkuEntity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<MskuToSkuEntity> entityList) {
        this.entityList = entityList;
    }

    public String getStoreSku() {
        return storeSku;
    }

    public void setStoreSku(String storeSku) {
        this.storeSku = storeSku;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }

    public int getTransportQty() {
        return transportQty;
    }

    public void setTransportQty(int transportQty) {
        this.transportQty = transportQty;
    }

    public int getPanyuStockQty() {
        return panyuStockQty;
    }

    public void setPanyuStockQty(int panyuStockQty) {
        this.panyuStockQty = panyuStockQty;
    }

    public int getVirtualStockQty() {
        return virtualStockQty;
    }

    public void setVirtualStockQty(int virtualStockQty) {
        this.virtualStockQty = virtualStockQty;
    }

    public int getOverseasTransportQty() {
        return overseasTransportQty;
    }

    public void setOverseasTransportQty(int overseasTransportQty) {
        this.overseasTransportQty = overseasTransportQty;
    }

   /* public int getFbaReturnQty() {
        return fbaReturnQty;
    }

    public void setFbaReturnQty(int fbaReturnQty) {
        this.fbaReturnQty = fbaReturnQty;
    }*/

    public int getOverseasStockQty() {
        return overseasStockQty;
    }

    public void setOverseasStockQty(int overseasStockQty) {
        this.overseasStockQty = overseasStockQty;
    }

   /* public int getFbaTransportQty() {
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
    }*/

    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public String toString() {
        return "WsRmpWarehouseStockSumEntity{" +
                "storeSku='" + storeSku + '\'' +
                ", cnName=" + cnName +
                ", productQty='" + productQty + '\'' +
                ", transportQty='" + transportQty + '\'' +
                ", panyuStockQty='" + panyuStockQty + '\'' +
                ", virtualStockQty='" + virtualStockQty + '\'' +
                ", overseasTransportQty='" + overseasTransportQty + '\'' +
                ", overseasStockQty='" + overseasStockQty + '\'' +
                ", totalQty='" + totalQty + '\'' +
                ", entityList='" + entityList + '\'' +
                '}';

    }
}
