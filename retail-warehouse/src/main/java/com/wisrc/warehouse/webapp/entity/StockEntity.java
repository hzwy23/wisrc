package com.wisrc.warehouse.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class StockEntity {
    @ApiModelProperty(value = "商品ID")
    private String skuId;
    @ApiModelProperty(value = "商品名称")
    private String skuName;
    @ApiModelProperty(value = "仓库ID")
    private String warehouseId;
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;
    @ApiModelProperty(value = "总库存量")
    private Integer totalSum;
    @ApiModelProperty(value = "在途库存")
    private Integer onWayStock;
    @ApiModelProperty(value = "在仓库存")
    private Integer sumStock;
    @ApiModelProperty(value = "可用库存数量")
    private Integer enableStockNum;
    @ApiModelProperty(value = "最近出入时间")
    private String lastInoutTime;

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

    public Integer getSumStock() {
        return sumStock;
    }

    public void setSumStock(Integer sumStock) {
        this.sumStock = sumStock;
    }

    public Integer getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Integer totalSum) {
        this.totalSum = totalSum;
    }

    public Integer getOnWayStock() {
        return onWayStock;
    }

    public void setOnWayStock(Integer onWayStock) {
        this.onWayStock = onWayStock;
    }

    public Integer getEnableStockNum() {
        return enableStockNum;
    }

    public void setEnableStockNum(Integer enableStockNum) {
        this.enableStockNum = enableStockNum;
    }

    public String getLastInoutTime() {
        return lastInoutTime;
    }

    public void setLastInoutTime(String lastInoutTime) {
        this.lastInoutTime = lastInoutTime;
    }
}
