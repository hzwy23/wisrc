package com.wisrc.wms.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class PackInfoVO {
    @ApiModelProperty("装箱长")
    private double outerBoxSpecificationLen;
    @ApiModelProperty("装箱宽")
    private double outerBoxSpecificationWidth;
    @ApiModelProperty("装箱高")
    private double outerBoxSpecificationHeight;
    @ApiModelProperty("箱数")
    private int numberOfBoxes;
    @ApiModelProperty("装量")
    private int packingQuantity;
    @ApiModelProperty("装箱重量")
    private double packingWeight;
    @ApiModelProperty("这种装箱规格下的发货数量")
    private double deliveryNumber;

    public double getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(double deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public double getOuterBoxSpecificationLen() {
        return outerBoxSpecificationLen;
    }

    public void setOuterBoxSpecificationLen(double outerBoxSpecificationLen) {
        this.outerBoxSpecificationLen = outerBoxSpecificationLen;
    }

    public double getOuterBoxSpecificationWidth() {
        return outerBoxSpecificationWidth;
    }

    public void setOuterBoxSpecificationWidth(double outerBoxSpecificationWidth) {
        this.outerBoxSpecificationWidth = outerBoxSpecificationWidth;
    }

    public double getOuterBoxSpecificationHeight() {
        return outerBoxSpecificationHeight;
    }

    public void setOuterBoxSpecificationHeight(double outerBoxSpecificationHeight) {
        this.outerBoxSpecificationHeight = outerBoxSpecificationHeight;
    }

    public int getNumberOfBoxes() {
        return numberOfBoxes;
    }

    public void setNumberOfBoxes(int numberOfBoxes) {
        this.numberOfBoxes = numberOfBoxes;
    }

    public int getPackingQuantity() {
        return packingQuantity;
    }

    public void setPackingQuantity(int packingQuantity) {
        this.packingQuantity = packingQuantity;
    }

    public double getPackingWeight() {
        return packingWeight;
    }

    public void setPackingWeight(double packingWeight) {
        this.packingWeight = packingWeight;
    }
}
