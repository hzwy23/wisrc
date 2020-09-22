package com.wisrc.warehouse.webapp.vo.syncVO;

import io.swagger.annotations.ApiModelProperty;

public class TransferOrderPackSyncVO {

    @ApiModelProperty(value = "箱号")
    private String boxNumber;

    @ApiModelProperty(value = "是否是标准箱")
    private String standard;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "装箱量")
    private int packageCapacity;

    @ApiModelProperty(value = "装箱重量")
    private Double weight;

    @ApiModelProperty(value = "装箱尺寸")
    private String size;

    @ApiModelProperty(value = "总数")
    private int totalQuantity;

    @ApiModelProperty(value = "箱数")
    private int packageQuantity;

    public int getPackageCapacity() {
        return packageCapacity;
    }

    public void setPackageCapacity(int packageCapacity) {
        this.packageCapacity = packageCapacity;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getPackageQuantity() {
        return packageQuantity;
    }

    public void setPackageQuantity(int packageQuantity) {
        this.packageQuantity = packageQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }
}
