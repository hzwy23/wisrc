package com.wisrc.replenishment.webapp.vo.wms;

import io.swagger.annotations.ApiModelProperty;

public class TransferOrderPackDetailsVO {

    @ApiModelProperty(value = "外装箱规格-长")
    private Double outerBoxSpecificationLen;

    @ApiModelProperty(value = "外装箱规格-宽")
    private Double outerBoxSpecificationWidth;

    @ApiModelProperty(value = "外装箱规格-高")
    private Double outerBoxSpecificationHeight;

    @ApiModelProperty(value = "重量")
    private Double packingWeight;

    @ApiModelProperty(value = "装量")
    private int packingQuantity;

    @ApiModelProperty(value = "箱数")
    private int numberOfBox;

    @ApiModelProperty(value = "发货数量")
    private int deliveryNumber;

    public Double getOuterBoxSpecificationLen() {
        return outerBoxSpecificationLen;
    }

    public void setOuterBoxSpecificationLen(Double outerBoxSpecificationLen) {
        this.outerBoxSpecificationLen = outerBoxSpecificationLen;
    }

    public Double getOuterBoxSpecificationWidth() {
        return outerBoxSpecificationWidth;
    }

    public void setOuterBoxSpecificationWidth(Double outerBoxSpecificationWidth) {
        this.outerBoxSpecificationWidth = outerBoxSpecificationWidth;
    }

    public Double getOuterBoxSpecificationHeight() {
        return outerBoxSpecificationHeight;
    }

    public void setOuterBoxSpecificationHeight(Double outerBoxSpecificationHeight) {
        this.outerBoxSpecificationHeight = outerBoxSpecificationHeight;
    }

    public Double getPackingWeight() {
        return packingWeight;
    }

    public void setPackingWeight(Double packingWeight) {
        this.packingWeight = packingWeight;
    }

    public int getPackingQuantity() {
        return packingQuantity;
    }

    public void setPackingQuantity(int packingQuantity) {
        this.packingQuantity = packingQuantity;
    }

    public int getNumberOfBox() {
        return numberOfBox;
    }

    public void setNumberOfBox(int numberOfBox) {
        this.numberOfBox = numberOfBox;
    }

    public int getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(int deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }
}