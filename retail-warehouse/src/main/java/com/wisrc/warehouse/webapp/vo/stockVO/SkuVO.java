package com.wisrc.warehouse.webapp.vo.stockVO;

import io.swagger.annotations.ApiModelProperty;

public class SkuVO {
    @ApiModelProperty(value = "时间")
    private String dataDt;
    @ApiModelProperty(value = "库存SKU")
    private String skuId;
    @ApiModelProperty(value = "库存SKU/产品名称")
    private String skuName;
    @ApiModelProperty(value = "本地仓在途生产中数量")
    private int productQty;
    @ApiModelProperty(value = "本地仓在途运输中数量")
    private int transportQty;
    @ApiModelProperty(value = "本地仓在仓番禺仓数量")
    private int panyuStockQty;
    @ApiModelProperty(value = "本地仓在仓虚拟仓数量")
    private int virtualStockQty;
    @ApiModelProperty(value = "海外仓在途国内调拨数量")
    private int overseasTransportQty;
    @ApiModelProperty(value = "海外仓在仓数量")
    private int overseasStockQty;
    @ApiModelProperty(value = "总计")
    private int totalQty;

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
}
