package com.wisrc.replenishment.webapp.vo.delivery;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;

public class CalFreightVO {

    @ApiModelProperty(value = "预估单价")
    private String unitPriceWithOil;//含油价 预估单价
    @ApiModelProperty(value = "预估计费重")
    private String totalWeight;//预估计费重
    @ApiModelProperty(value = "预估运费")
    private String freightTotal;//预估运费
    @ApiModelProperty(value = "附加费")
    private String annexCost;//附加费
    @ApiModelProperty(value = "费用合计")
    private String totalCost;//费用合计
    @ApiModelProperty(value = "类型")
    private String weighTypeCd;//类型。实重、抛重
    @ApiModelProperty(value = "预计签收日期")
    private Date estimateDate;//预计签收日期

    public String getUnitPriceWithOil() {
        return unitPriceWithOil;
    }

    public void setUnitPriceWithOil(String unitPriceWithOil) {
        this.unitPriceWithOil = unitPriceWithOil;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getFreightTotal() {
        return freightTotal;
    }

    public void setFreightTotal(String freightTotal) {
        this.freightTotal = freightTotal;
    }

    public String getAnnexCost() {
        return annexCost;
    }

    public void setAnnexCost(String annexCost) {
        this.annexCost = annexCost;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getWeighTypeCd() {
        return weighTypeCd;
    }

    public void setWeighTypeCd(String weighTypeCd) {
        this.weighTypeCd = weighTypeCd;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }
}
