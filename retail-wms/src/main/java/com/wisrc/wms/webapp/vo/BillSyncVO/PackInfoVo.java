package com.wisrc.wms.webapp.vo.BillSyncVO;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

public class PackInfoVo {
    @ApiModelProperty(value = "箱号")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String boxNumber;
    @ApiModelProperty(value = "是否标准箱")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int standard;
    @ApiModelProperty(value = "单位")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String unit;
    @ApiModelProperty(value = "装箱量")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int packageCapacity;
    @ApiModelProperty(value = "装箱重量")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private double weight;
    @ApiModelProperty(value = "装箱尺寸")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String size;
    @ApiModelProperty(value = "箱数")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int packageQuantity;
    @ApiModelProperty(value = "总数")
    @JsonInclude(JsonInclude.Include.NON_NULL)
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
