package com.wisrc.replenishment.webapp.vo.wms;

import io.swagger.annotations.ApiModelProperty;

public class InsertPackVO {
    @ApiModelProperty(value = "系统唯一编码")
    private String uuid;
    @ApiModelProperty(value = "补货单商品唯一ID")
    private String replenishmentCommodityId;
    @ApiModelProperty(value = "外箱规格-长(cm)")
    private double outerBoxSpecificationLen;
    @ApiModelProperty(value = "外箱规格-宽(cm)")
    private double outerBoxSpecificationWidth;
    @ApiModelProperty(value = "外箱规格-高(cm)")
    private double outerBoxSpecificationHeight;
    @ApiModelProperty(value = "装量(PCS/箱)")
    private int packingQuantity;
    @ApiModelProperty(value = "装箱箱数")
    private int numberOfBoxes;
    @ApiModelProperty(value = "重量(kg/箱)")
    private double packingWeight;
    @ApiModelProperty(value = "签收数量")
    private int signInQuantity;
    @ApiModelProperty(value = "补货数量")
    private int replenishmentQuantity;
    @ApiModelProperty(value = "发货数量")
    private double deliveryNumber;
    @ApiModelProperty(value = "生成时间")
    private String createTime;
    @ApiModelProperty(value = "删除标识")
    private int deleteStatus;
    @ApiModelProperty(value = "装箱数量")
    private int packingNumber;

    public int getPackingNumber() {
        return packingNumber;
    }

    public void setPackingNumber(int packingNumber) {
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

    public int getSignInQuantity() {
        return signInQuantity;
    }

    public void setSignInQuantity(int signInQuantity) {
        this.signInQuantity = signInQuantity;
    }

    public int getReplenishmentQuantity() {
        return replenishmentQuantity;
    }

    public void setReplenishmentQuantity(int replenishmentQuantity) {
        this.replenishmentQuantity = replenishmentQuantity;
    }

    public double getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(double deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
