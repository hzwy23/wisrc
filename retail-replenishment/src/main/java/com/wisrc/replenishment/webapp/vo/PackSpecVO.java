package com.wisrc.replenishment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class PackSpecVO {
    @ApiModelProperty(value = "装箱尺寸")
    private String packSize;
    @ApiModelProperty(value = "折算重量")
    private String conWeight;
    @ApiModelProperty(value = "装箱重量")
    private String packWeight;//装箱重量
    @ApiModelProperty(value = "计费重量")
    private String billWeight;//计费重量
    @ApiModelProperty(value = "类型 实重、抛重")
    private String packType;//类型，实重、抛重
    @ApiModelProperty(value = "装箱数")
    private String numberBox;

    public String getPackSize() {
        return packSize;
    }

    public void setPackSize(String packSize) {
        this.packSize = packSize;
    }

    public String getConWeight() {
        return conWeight;
    }

    public void setConWeight(String conWeight) {
        this.conWeight = conWeight;
    }

    public String getPackWeight() {
        return packWeight;
    }

    public void setPackWeight(String packWeight) {
        this.packWeight = packWeight;
    }

    public String getBillWeight() {
        return billWeight;
    }

    public void setBillWeight(String billWeight) {
        this.billWeight = billWeight;
    }

    public String getPackType() {
        return packType;
    }

    public void setPackType(String packType) {
        this.packType = packType;
    }

    public String getNumberBox() {
        return numberBox;
    }

    public void setNumberBox(String numberBox) {
        this.numberBox = numberBox;
    }

    @Override
    public String toString() {
        return "PackSpecVO{" +
                "packSize='" + packSize + '\'' +
                ", conWeight='" + conWeight + '\'' +
                ", packWeight='" + packWeight + '\'' +
                ", billWeight='" + billWeight + '\'' +
                ", packType='" + packType + '\'' +
                ", numberBox='" + numberBox + '\'' +
                '}';
    }
}
