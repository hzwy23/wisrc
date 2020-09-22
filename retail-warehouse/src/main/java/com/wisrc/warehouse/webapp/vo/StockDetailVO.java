package com.wisrc.warehouse.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class StockDetailVO {
    @ApiModelProperty(value = "fnSkuId")
    private String fnSkuId;
    @ApiModelProperty(value = "分仓")
    private String subWarehouseName;
    @ApiModelProperty(value = "库位")
    private String warehousePositionId;
    @ApiModelProperty(value = "入库批次")
    private String enterBatch;
    @ApiModelProperty(value = "生产批次")
    private String productionBatch;
    @ApiModelProperty(value = "库存总量")
    private int sumStock;
    @ApiModelProperty(value = "预分配库存")
    private int distributeStockNum;
    @ApiModelProperty(value = "不可用库存")
    private int unableStock;
    @ApiModelProperty(value = "可用库存")
    private int enableStockNum;

    public String getFnSkuId() {
        return fnSkuId;
    }

    public void setFnSkuId(String fnSkuId) {
        this.fnSkuId = fnSkuId;
    }

    public String getSubWarehouseName() {
        return subWarehouseName;
    }

    public void setSubWarehouseName(String subWarehouseName) {
        this.subWarehouseName = subWarehouseName;
    }

    public String getWarehousePositionId() {
        return warehousePositionId;
    }

    public void setWarehousePositionId(String warehousePositionId) {
        this.warehousePositionId = warehousePositionId;
    }

    public String getEnterBatch() {
        return enterBatch;
    }

    public void setEnterBatch(String enterBatch) {
        this.enterBatch = enterBatch;
    }

    public String getProductionBatch() {
        return productionBatch;
    }

    public void setProductionBatch(String productionBatch) {
        this.productionBatch = productionBatch;
    }

    public int getSumStock() {
        return sumStock;
    }

    public void setSumStock(int sumStock) {
        this.sumStock = sumStock;
    }

    public int getDistributeStockNum() {
        return distributeStockNum;
    }

    public void setDistributeStockNum(int distributeStockNum) {
        this.distributeStockNum = distributeStockNum;
    }

    public int getUnableStock() {
        return unableStock;
    }

    public void setUnableStock(int unableStock) {
        this.unableStock = unableStock;
    }

    public int getEnableStockNum() {
        return enableStockNum;
    }

    public void setEnableStockNum(int enableStockNum) {
        this.enableStockNum = enableStockNum;
    }
}
