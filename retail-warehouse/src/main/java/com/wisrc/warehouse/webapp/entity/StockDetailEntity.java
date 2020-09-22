package com.wisrc.warehouse.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class StockDetailEntity {
    @ApiModelProperty(value = "库存明细Id")
    private String stockDetailId;
    @ApiModelProperty(value = "库存管理Id")
    private String stockId;
    @ApiModelProperty(value = "fnSkuId")
    private String fnSkuId;
    @ApiModelProperty(value = "库位")
    private String warehousePosition;
    @ApiModelProperty(value = "预分配库存")
    private int distributeStockNum;
    @ApiModelProperty(value = "不可用库存")
    private int disableStockNum;
    @ApiModelProperty(value = "库存总量")
    private int stockSumNum;
    @ApiModelProperty(value = "可用库存")
    private int enableStockNum;

    public String getStockDetailId() {
        return stockDetailId;
    }

    public void setStockDetailId(String stockDetailId) {
        this.stockDetailId = stockDetailId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getFnSkuId() {
        return fnSkuId;
    }

    public void setFnSkuId(String fnSkuId) {
        this.fnSkuId = fnSkuId;
    }

    public String getWarehousePosition() {
        return warehousePosition;
    }

    public void setWarehousePosition(String warehousePosition) {
        this.warehousePosition = warehousePosition;
    }

    public int getDistributeStockNum() {
        return distributeStockNum;
    }

    public void setDistributeStockNum(int distributeStockNum) {
        this.distributeStockNum = distributeStockNum;
    }

    public int getDisableStockNum() {
        return disableStockNum;
    }

    public void setDisableStockNum(int disableStockNum) {
        this.disableStockNum = disableStockNum;
    }

    public int getStockSumNum() {
        return stockSumNum;
    }

    public void setStockSumNum(int stockSumNum) {
        this.stockSumNum = stockSumNum;
    }

    public int getEnableStockNum() {
        return enableStockNum;
    }

    public void setEnableStockNum(int enableStockNum) {
        this.enableStockNum = enableStockNum;
    }
}
