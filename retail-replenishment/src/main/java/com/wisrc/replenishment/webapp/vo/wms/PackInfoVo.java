package com.wisrc.replenishment.webapp.vo.wms;

import io.swagger.annotations.ApiModelProperty;

public class PackInfoVo {
    @ApiModelProperty(value = "箱号")
    private String boxNumber;
    @ApiModelProperty(value = "是否标准箱")
    private int standard;
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "装箱量")
    private int packageCapacity;
    @ApiModelProperty(value = "装箱重量")
    private double weight;
    @ApiModelProperty(value = "装箱尺寸")
    private String size;
    @ApiModelProperty(value = "箱数")
    private int packageQuantity;
    @ApiModelProperty(value = "总数")
    private int totalQuantity;

    public String getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }

    public int getStandard() {
        return standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getPackageCapacity() {
        return packageCapacity;
    }

    public void setPackageCapacity(int packageCapacity) {
        this.packageCapacity = packageCapacity;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getPackageQuantity() {
        return packageQuantity;
    }

    public void setPackageQuantity(int packageQuantity) {
        this.packageQuantity = packageQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
