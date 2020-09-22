package com.wisrc.warehouse.webapp.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;

@Data
public class WarehouseStockManagerSyncEntity {
    private String warehouseId;
    private String warehouseName;
    private String subWarehouseId;
    private String subWarehouseName;
    private String warehouseZoneId;
    private String warehouseZoneName;
    private String warehousePositionId;
    private String warehousePosition;
    private String skuId;
    private String skuName;
    private String fnSkuId;
    private String enterBatch;
    private String productionBatch;
    private Integer sumStock;
    private Integer onWayStock;
    private Integer enableStockNum;
    private Integer freezeStockNum;
    private Integer assignedNum;
    private Integer waitUpNum;
    private Integer replenishmentWaitDownNum;
    private Integer replenishmentWaitUpNum;
    private String bId;
    private String mskuId;
    private String shopId;
    private Timestamp lastInoutTime;

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

    public String getWarehouseZoneId() {
        return warehouseZoneId;
    }

    public void setWarehouseZoneId(String warehouseZoneId) {
        this.warehouseZoneId = warehouseZoneId;
    }

    public String getWarehouseZoneName() {
        return warehouseZoneName;
    }

    public void setWarehouseZoneName(String warehouseZoneName) {
        this.warehouseZoneName = warehouseZoneName;
    }

    public String getWarehousePositionId() {
        return warehousePositionId;
    }

    public void setWarehousePositionId(String warehousePositionId) {
        this.warehousePositionId = warehousePositionId;
    }

    public String getWarehousePosition() {
        return warehousePosition;
    }

    public void setWarehousePosition(String warehousePosition) {
        this.warehousePosition = warehousePosition;
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

    public String getFnSkuId() {
        return fnSkuId;
    }

    public void setFnSkuId(String fnSkuId) {
        this.fnSkuId = fnSkuId;
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

    public Integer getSumStock() {
        return sumStock;
    }

    public void setSumStock(Integer sumStock) {
        this.sumStock = sumStock;
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

    public Integer getFreezeStockNum() {
        return freezeStockNum;
    }

    public void setFreezeStockNum(Integer freezeStockNum) {
        this.freezeStockNum = freezeStockNum;
    }

    public Integer getAssignedNum() {
        return assignedNum;
    }

    public void setAssignedNum(Integer assignedNum) {
        this.assignedNum = assignedNum;
    }

    public Integer getWaitUpNum() {
        return waitUpNum;
    }

    public void setWaitUpNum(Integer waitUpNum) {
        this.waitUpNum = waitUpNum;
    }

    public Integer getReplenishmentWaitDownNum() {
        return replenishmentWaitDownNum;
    }

    public void setReplenishmentWaitDownNum(Integer replenishmentWaitDownNum) {
        this.replenishmentWaitDownNum = replenishmentWaitDownNum;
    }

    public Integer getReplenishmentWaitUpNum() {
        return replenishmentWaitUpNum;
    }

    public void setReplenishmentWaitUpNum(Integer replenishmentWaitUpNum) {
        this.replenishmentWaitUpNum = replenishmentWaitUpNum;
    }

    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Timestamp getLastInoutTime() {
        return lastInoutTime;
    }

    public void setLastInoutTime(Timestamp lastInoutTime) {
        this.lastInoutTime = lastInoutTime;
    }

}
