package com.wisrc.shipment.webapp.entity;

import java.math.BigDecimal;

public class ProposalSchemeEntity {
    private String uuid;

    private String replenishmentId;

    private String originatingWarehouseId;

    private Integer deliveringAmount;

    private Integer shipTypeCd;

    private String shipmentId;

    private String effectiveDays;

    private BigDecimal unitPrice;

    private BigDecimal forecastFreight;

    private BigDecimal totalPrice;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getReplenishmentId() {
        return replenishmentId;
    }

    public void setReplenishmentId(String replenishmentId) {
        this.replenishmentId = replenishmentId;
    }

    public String getOriginatingWarehouseId() {
        return originatingWarehouseId;
    }

    public void setOriginatingWarehouseId(String originatingWarehouseId) {
        this.originatingWarehouseId = originatingWarehouseId;
    }

    public Integer getDeliveringAmount() {
        return deliveringAmount;
    }

    public void setDeliveringAmount(Integer deliveringAmount) {
        this.deliveringAmount = deliveringAmount;
    }

    public Integer getShipTypeCd() {
        return shipTypeCd;
    }

    public void setShipTypeCd(Integer shipTypeCd) {
        this.shipTypeCd = shipTypeCd;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getEffectiveDays() {
        return effectiveDays;
    }

    public void setEffectiveDays(String effectiveDays) {
        this.effectiveDays = effectiveDays;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getForecastFreight() {
        return forecastFreight;
    }

    public void setForecastFreight(BigDecimal forecastFreight) {
        this.forecastFreight = forecastFreight;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}