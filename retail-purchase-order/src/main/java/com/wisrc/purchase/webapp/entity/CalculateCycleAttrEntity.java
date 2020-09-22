package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

public class CalculateCycleAttrEntity {
    @ApiModelProperty(value = "计算周期id")
    private Integer calculateCycleCd;
    @ApiModelProperty(value = "计算周期描述")
    private String calculateCycleDesc;

    public Integer getCalculateCycleCd() {
        return calculateCycleCd;
    }

    public void setCalculateCycleCd(Integer calculateCycleCd) {
        this.calculateCycleCd = calculateCycleCd;
    }

    public String getCalculateCycleDesc() {
        return calculateCycleDesc;
    }

    public void setCalculateCycleDesc(String calculateCycleDesc) {
        this.calculateCycleDesc = calculateCycleDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculateCycleAttrEntity that = (CalculateCycleAttrEntity) o;
        return Objects.equals(calculateCycleCd, that.calculateCycleCd) &&
                Objects.equals(calculateCycleDesc, that.calculateCycleDesc);
    }

    @Override
    public int hashCode() {

        return Objects.hash(calculateCycleCd, calculateCycleDesc);
    }
}
