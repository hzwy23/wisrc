package com.wisrc.shipment.webapp.vo;

public class WareHouseVo {
    private Integer changeQuantity;
    private String fnsku;
    private String warehousePosition;
    private String changeLabelId;
    private String enterBatch;
    private String productionBatch;
    private Integer eableStockNum;
    private Integer onWarehouseStockNum;
    private String subWarehouseId;
    private String subWarehouseName;

    public Integer getChangeQuantity() {
        return changeQuantity;
    }

    public void setChangeQuantity(Integer changeQuantity) {
        this.changeQuantity = changeQuantity;
    }

    public String getFnsku() {
        return fnsku;
    }

    public void setFnsku(String fnsku) {
        this.fnsku = fnsku;
    }

    public String getWarehousePosition() {
        return warehousePosition;
    }

    public void setWarehousePosition(String warehousePosition) {
        this.warehousePosition = warehousePosition;
    }

    public String getChangeLabelId() {
        return changeLabelId;
    }

    public void setChangeLabelId(String changeLabelId) {
        this.changeLabelId = changeLabelId;
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

    public Integer getEableStockNum() {
        return eableStockNum;
    }

    public void setEableStockNum(Integer eableStockNum) {
        this.eableStockNum = eableStockNum;
    }

    public Integer getOnWarehouseStockNum() {
        return onWarehouseStockNum;
    }

    public void setOnWarehouseStockNum(Integer onWarehouseStockNum) {
        this.onWarehouseStockNum = onWarehouseStockNum;
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
