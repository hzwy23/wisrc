package com.wisrc.warehouse.webapp.vo;

public class FnSkuStockVO {
    private String fnSkuId;
    private String skuId;
    private String skuName;
    private String warehouseId;
    private String warehouseName;
    private String warehousePosition;
    private String enterBatch;
    private String productionBatch;
    private int onWarehouseStockNum;
    private int enableStockNum;
    private String subWarehouseId;
    private String subWarehouseName;

    public String getFnSkuId() {
        return fnSkuId;
    }

    public void setFnSkuId(String fnSkuId) {
        this.fnSkuId = fnSkuId;
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

    public String getWarehousePosition() {
        return warehousePosition;
    }

    public void setWarehousePosition(String warehousePosition) {
        this.warehousePosition = warehousePosition;
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

    public int getOnWarehouseStockNum() {
        return onWarehouseStockNum;
    }

    public void setOnWarehouseStockNum(int onWarehouseStockNum) {
        this.onWarehouseStockNum = onWarehouseStockNum;
    }

    public int getEnableStockNum() {
        return enableStockNum;
    }

    public void setEnableStockNum(int enableStockNum) {
        this.enableStockNum = enableStockNum;
    }

    public String getSubWarehouseId() {
        return subWarehouseId;
    }

    public void setSubWarehouseId(String subWarehouseId) {
        this.subWarehouseId = subWarehouseId;
    }

    public String getSubWarehouseName() {
        return subWarehouseName;
    }

    public void setSubWarehouseName(String subWarehouseName) {
        this.subWarehouseName = subWarehouseName;
    }
}
