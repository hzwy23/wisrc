package com.wisrc.order.webapp.vo;

public class WareHouseVo {
    private String warehouseId;
    private String warehouseName;
    private Integer enableStockNum;

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

    public Integer getEnableStockNum() {
        return enableStockNum;
    }

    public void setEnableStockNum(Integer enableStockNum) {
        this.enableStockNum = enableStockNum;
    }
}
