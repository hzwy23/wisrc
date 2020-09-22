package com.wisrc.replenishment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class ReplenishShippingDataVO {
    @ApiModelProperty(value = "产品信息唯一标识")
    private String uuid;
    @ApiModelProperty(value = "补货产品Id")
    private String replenishmentCommodityId;
    @ApiModelProperty(value = "装箱尺寸--长")
    private int outerBoxSpecificationLen;
    @ApiModelProperty(value = "装箱尺寸--宽")
    private int outerBoxSpecificationWidth;
    @ApiModelProperty(value = "装箱尺寸--高")
    private int outerBoxSpecificationHeight;
    @ApiModelProperty(value = "箱数")
    private int numberOfBoxes;
    @ApiModelProperty(value = "装量")
    private int packingQuantity;
    @ApiModelProperty(value = "重量（KG/箱）")
    private double packingWeight;
    @ApiModelProperty(value = "装箱数量")
    private double packingNumber;
    @ApiModelProperty(value = "发货数量")
    private double deliveryNumber;


    public double getPackingNumber() {
        return packingNumber;
    }

    public void setPackingNumber(double packingNumber) {
        this.packingNumber = packingNumber;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getReplenishmentCommodityId() {
        return replenishmentCommodityId;
    }

    public void setReplenishmentCommodityId(String replenishmentCommodityId) {
        this.replenishmentCommodityId = replenishmentCommodityId;
    }

    public int getOuterBoxSpecificationLen() {
        return outerBoxSpecificationLen;
    }

    public void setOuterBoxSpecificationLen(int outerBoxSpecificationLen) {
        this.outerBoxSpecificationLen = outerBoxSpecificationLen;
    }

    public int getOuterBoxSpecificationWidth() {
        return outerBoxSpecificationWidth;
    }

    public void setOuterBoxSpecificationWidth(int outerBoxSpecificationWidth) {
        this.outerBoxSpecificationWidth = outerBoxSpecificationWidth;
    }

    public int getOuterBoxSpecificationHeight() {
        return outerBoxSpecificationHeight;
    }

    public void setOuterBoxSpecificationHeight(int outerBoxSpecificationHeight) {
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

    public double getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(double deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }
}
