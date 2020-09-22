package com.wisrc.wms.webapp.vo.BillSyncVO;

import io.swagger.annotations.ApiModelProperty;

public class TransferOrderPackSyncVO {

    @ApiModelProperty(value = "箱号")
    private String boxNumber;

    @ApiModelProperty(value = "是否是标准箱")
    private String standard;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "装箱量")
    private String packageCapacity;

    @ApiModelProperty(value = "装箱重量")
    private String weight;

    @ApiModelProperty(value = "装箱尺寸")
    private String size;

    @ApiModelProperty(value = "总数")
    private String totalQuantity;

    @ApiModelProperty(value = "箱数")
    private String packageQuantity;

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPackageQuantity() {
        return packageQuantity;
    }

    public void setPackageQuantity(String packageQuantity) {
        this.packageQuantity = packageQuantity;
    }

    public String getPackageCapacity() {
        return packageCapacity;
    }

    public void setPackageCapacity(String packageCapacity) {
        this.packageCapacity = packageCapacity;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
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
