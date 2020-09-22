package com.wisrc.warehouse.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class WsRmpWarehouseStockSumEntity {
    @ApiModelProperty(value = "库存SKU")
    private String skuId;
    @ApiModelProperty(value = "库存SKU/产品名称")
    private String skuName;
    @ApiModelProperty(value = "生产中数量")
    private int productQty;
    @ApiModelProperty(value = "本地仓在途数量")
    private int localOnwayQty;
    @ApiModelProperty(value = "本地仓在仓数量")
    private int localStockQty;
    @ApiModelProperty(value = "虚拟仓数量")
    private int virtualStockQty;
    @ApiModelProperty(value = "海外仓在途国内调拨数量")
    private int overseasTransportQty;
    @ApiModelProperty(value = "海外仓在仓数量")
    private int overseasStockQty;
    @ApiModelProperty(value = "海外仓在仓数量")
    private int totalQty;

    private List<MskuToSkuEntity> mskuList;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public int getProductQty() {
        return productQty;
    }

    public void setProductQty(int productQty) {
        this.productQty = productQty;
    }

    public int getLocalOnwayQty() {
        return localOnwayQty;
    }

    public void setLocalOnwayQty(int localOnwayQty) {
        this.localOnwayQty = localOnwayQty;
    }

    public int getLocalStockQty() {
        return localStockQty;
    }

    public void setLocalStockQty(int localStockQty) {
        this.localStockQty = localStockQty;
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

    public int getOverseasStockQty() {
        return overseasStockQty;
    }

    public void setOverseasStockQty(int overseasStockQty) {
        this.overseasStockQty = overseasStockQty;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public List<MskuToSkuEntity> getMskuList() {
        return mskuList;
    }

    public void setMskuList(List<MskuToSkuEntity> mskuList) {
        this.mskuList = mskuList;
    }
}
