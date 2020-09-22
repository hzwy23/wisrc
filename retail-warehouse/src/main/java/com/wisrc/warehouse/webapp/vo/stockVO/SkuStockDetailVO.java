package com.wisrc.warehouse.webapp.vo.stockVO;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class SkuStockDetailVO {
    @ApiModelProperty(value = "报表日期")
    private Date asOfDate;
    @ApiModelProperty(value = "skuId")
    private String skuId;
    @ApiModelProperty(value = "仓库ID")
    private String warehouseId;
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;
    @ApiModelProperty(value = "本地仓在仓")
    private int localQty;
    @ApiModelProperty(value = "海外仓在仓")
    private int overseaQty;
    @ApiModelProperty(value = "虚拟仓在仓")
    private int virtualQty;

    public Date getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(Date asOfDate) {
        this.asOfDate = asOfDate;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public int getLocalQty() {
        return localQty;
    }

    public void setLocalQty(int localQty) {
        this.localQty = localQty;
    }

    public int getOverseaQty() {
        return overseaQty;
    }

    public void setOverseaQty(int overseaQty) {
        this.overseaQty = overseaQty;
    }

    public int getVirtualQty() {
        return virtualQty;
    }

    public void setVirtualQty(int virtualQty) {
        this.virtualQty = virtualQty;
    }
}
