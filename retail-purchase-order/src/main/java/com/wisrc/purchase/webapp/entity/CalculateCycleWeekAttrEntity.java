package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

public class CalculateCycleWeekAttrEntity {
    @ApiModelProperty(value = "计算量度id")
    private Integer calculateCycleWeekCd;
    @ApiModelProperty(value = "计算量度描述--中文")
    private String calculateCycleWeekDesc;
    @ApiModelProperty(value = "计算量度描述--英文简写")
    private Integer calculateCycleWeekAttr;

    public Integer getCalculateCycleWeekCd() {
        return calculateCycleWeekCd;
    }

    public void setCalculateCycleWeekCd(Integer calculateCycleWeekCd) {
        this.calculateCycleWeekCd = calculateCycleWeekCd;
    }

    public String getCalculateCycleWeekDesc() {
        return calculateCycleWeekDesc;
    }

    public void setCalculateCycleWeekDesc(String calculateCycleWeekDesc) {
        this.calculateCycleWeekDesc = calculateCycleWeekDesc;
    }

    public Integer getCalculateCycleWeekAttr() {
        return calculateCycleWeekAttr;
    }

    public void setCalculateCycleWeekAttr(Integer calculateCycleWeekAttr) {
        this.calculateCycleWeekAttr = calculateCycleWeekAttr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculateCycleWeekAttrEntity that = (CalculateCycleWeekAttrEntity) o;
        return Objects.equals(calculateCycleWeekCd, that.calculateCycleWeekCd) &&
                Objects.equals(calculateCycleWeekDesc, that.calculateCycleWeekDesc) &&
                Objects.equals(calculateCycleWeekAttr, that.calculateCycleWeekAttr);
    }

    @Override
    public int hashCode() {

        return Objects.hash(calculateCycleWeekCd, calculateCycleWeekDesc, calculateCycleWeekAttr);
    }
}
