package com.wisrc.replenishment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class FbaMskuPackQueryVO {

    @ApiModelProperty(value = "系统唯一标识id", hidden = true)
    private String uuid;
    @ApiModelProperty(value = "外箱规格-长(cm)")
    private int outerBoxSpecificationLen;
    @ApiModelProperty(value = "外箱规格-宽(cm)")
    private int outerBoxSpecificationWidth;
    @ApiModelProperty(value = "外箱规格-高(cm)")
    private int outerBoxSpecificationHeight;
    @ApiModelProperty(value = "装量(PCS/箱)")
    private int packingQuantity;
    @ApiModelProperty(value = "装箱箱数")
    private int numberOfBoxes;
    @ApiModelProperty(value = "重量(kg/箱)")
    private double packingWeight;
    @ApiModelProperty(value = "补货量")
    private int replenishmentQuantity;
    @ApiModelProperty(value = "发货量", hidden = true)
    private int deliveryNumber;
    @ApiModelProperty(value = "签收数量", hidden = true)
    private int signInQuantity;
    @ApiModelProperty(value = "是否标准箱")
    private int isStandard;
    @ApiModelProperty(value = "装箱数量")
    private int packingNumber;

    public int getPackingNumber() {
        return packingNumber;
    }

    public void setPackingNumber(int packingNumber) {
        this.packingNumber = packingNumber;
    }

    public int getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(int isStandard) {
        this.isStandard = isStandard;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public int getPackingQuantity() {
        return packingQuantity;
    }

    public void setPackingQuantity(int packingQuantity) {
        this.packingQuantity = packingQuantity;
    }

    public int getNumberOfBoxes() {
        return numberOfBoxes;
    }

    public void setNumberOfBoxes(int numberOfBoxes) {
        this.numberOfBoxes = numberOfBoxes;
    }

    public double getPackingWeight() {
        return packingWeight;
    }

    public void setPackingWeight(double packingWeight) {
        this.packingWeight = packingWeight;
    }

    public int getReplenishmentQuantity() {
        return replenishmentQuantity;
    }

    public void setReplenishmentQuantity(int replenishmentQuantity) {
        this.replenishmentQuantity = replenishmentQuantity;
    }

    public int getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(int deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public int getSignInQuantity() {
        return signInQuantity;
    }

    public void setSignInQuantity(int signInQuantity) {
        this.signInQuantity = signInQuantity;
    }
}
