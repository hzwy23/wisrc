package com.wisrc.shipment.webapp.entity;

import java.util.Date;

public class FbaReplenishmentRemindEntity {
    private String replenishmentId;

    private String mskuId;

    private String shopId;

    private Integer warningTypeCd;

    private Integer fbaWarehouseStock;

    private Integer fbaWarehouseAvailableDays;

    private Integer fbaWayStock;

    private Integer fbaWayAvailableDays;

    private Integer estimatedDailySales;

    private Integer fbaAvailableDays;

    private Integer safeStockDays;

    private Integer replenishmentQuantity;

    private String estimatedWeight;

    private String estimatedVolume;

    private Date datetime;

    public String getReplenishmentId() {
        return replenishmentId;
    }

    public void setReplenishmentId(String replenishmentId) {
        this.replenishmentId = replenishmentId;
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

    public Integer getWarningTypeCd() {
        return warningTypeCd;
    }

    public void setWarningTypeCd(Integer warningTypeCd) {
        this.warningTypeCd = warningTypeCd;
    }

    public Integer getFbaWarehouseStock() {
        return fbaWarehouseStock;
    }

    public void setFbaWarehouseStock(Integer fbaWarehouseStock) {
        this.fbaWarehouseStock = fbaWarehouseStock;
    }

    public Integer getFbaWarehouseAvailableDays() {
        return fbaWarehouseAvailableDays;
    }

    public void setFbaWarehouseAvailableDays(Integer fbaWarehouseAvailableDays) {
        this.fbaWarehouseAvailableDays = fbaWarehouseAvailableDays;
    }

    public Integer getFbaWayStock() {
        return fbaWayStock;
    }

    public void setFbaWayStock(Integer fbaWayStock) {
        this.fbaWayStock = fbaWayStock;
    }

    public Integer getFbaWayAvailableDays() {
        return fbaWayAvailableDays;
    }

    public void setFbaWayAvailableDays(Integer fbaWayAvailableDays) {
        this.fbaWayAvailableDays = fbaWayAvailableDays;
    }

    public Integer getEstimatedDailySales() {
        return estimatedDailySales;
    }

    public void setEstimatedDailySales(Integer estimatedDailySales) {
        this.estimatedDailySales = estimatedDailySales;
    }

    public Integer getFbaAvailableDays() {
        return fbaAvailableDays;
    }

    public void setFbaAvailableDays(Integer fbaAvailableDays) {
        this.fbaAvailableDays = fbaAvailableDays;
    }

    public Integer getSafeStockDays() {
        return safeStockDays;
    }

    public void setSafeStockDays(Integer safeStockDays) {
        this.safeStockDays = safeStockDays;
    }

    public Integer getReplenishmentQuantity() {
        return replenishmentQuantity;
    }

    public void setReplenishmentQuantity(Integer replenishmentQuantity) {
        this.replenishmentQuantity = replenishmentQuantity;
    }

    public String getEstimatedWeight() {
        return estimatedWeight;
    }

    public void setEstimatedWeight(String estimatedWeight) {
        this.estimatedWeight = estimatedWeight;
    }

    public String getEstimatedVolume() {
        return estimatedVolume;
    }

    public void setEstimatedVolume(String estimatedVolume) {
        this.estimatedVolume = estimatedVolume;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

}