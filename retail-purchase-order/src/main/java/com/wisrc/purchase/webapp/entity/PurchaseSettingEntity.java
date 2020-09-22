package com.wisrc.purchase.webapp.entity;

import java.sql.Time;
import java.util.Objects;

public class PurchaseSettingEntity {
    private int stockCycle;
    private int calculateCycleCd;
    private int purchaseWarmDay;
    private Time datetime;
    private Integer calculateCycleWeekCd;

    public int getStockCycle() {
        return stockCycle;
    }

    public void setStockCycle(int stockCycle) {
        this.stockCycle = stockCycle;
    }

    public int getCalculateCycleCd() {
        return calculateCycleCd;
    }

    public void setCalculateCycleCd(int calculateCycleCd) {
        this.calculateCycleCd = calculateCycleCd;
    }

    public int getPurchaseWarmDay() {
        return purchaseWarmDay;
    }

    public void setPurchaseWarmDay(int purchaseWarmDay) {
        this.purchaseWarmDay = purchaseWarmDay;
    }

    public Time getDatetime() {
        return datetime;
    }

    public void setDatetime(Time datetime) {
        this.datetime = datetime;
    }

    public Integer getCalculateCycleWeekCd() {
        return calculateCycleWeekCd;
    }

    public void setCalculateCycleWeekCd(Integer calculateCycleWeekCd) {
        this.calculateCycleWeekCd = calculateCycleWeekCd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseSettingEntity that = (PurchaseSettingEntity) o;
        return stockCycle == that.stockCycle &&
                calculateCycleCd == that.calculateCycleCd &&
                purchaseWarmDay == that.purchaseWarmDay &&
                Objects.equals(datetime, that.datetime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(stockCycle, calculateCycleCd, purchaseWarmDay, datetime);
    }
}
