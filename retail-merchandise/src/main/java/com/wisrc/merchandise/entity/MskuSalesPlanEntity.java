package com.wisrc.merchandise.entity;

import java.util.Date;
import java.util.Objects;

public class MskuSalesPlanEntity {
    private String planId;
    private Integer expectedDailySales;
    private Double guidePrice;
    private Integer salesStatusCd;
    private Date startDate;
    private Date expiryDate;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public Integer getExpectedDailySales() {
        return expectedDailySales;
    }

    public void setExpectedDailySales(Integer expectedDailySales) {
        this.expectedDailySales = expectedDailySales;
    }

    public Integer getSalesStatusCd() {
        return salesStatusCd;
    }

    public void setSalesStatusCd(Integer salesStatusCd) {
        this.salesStatusCd = salesStatusCd;
    }

    public Double getGuidePrice() {
        return guidePrice;
    }

    public void setGuidePrice(Double guidePrice) {
        this.guidePrice = guidePrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MskuSalesPlanEntity that = (MskuSalesPlanEntity) o;
        return Objects.equals(planId, that.planId) &&
                Objects.equals(expectedDailySales, that.expectedDailySales) &&
                Objects.equals(guidePrice, that.guidePrice) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public String toString() {
        return "MskuSalesPlanEntity{" +
                "planId='" + planId + '\'' +
                ", expectedDailySales=" + expectedDailySales +
                ", guidePrice=" + guidePrice +
                ", salesStatusCd=" + salesStatusCd +
                ", startDate=" + startDate +
                ", expiryDate=" + expiryDate +
                '}';
    }

    @Override
    public int hashCode() {

        return Objects.hash(planId, expectedDailySales, guidePrice, startDate, expiryDate);
    }
}
