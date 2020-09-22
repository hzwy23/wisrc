package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

/**
 * 补货单商品装箱规格表
 */
public class FbaMskuPackInfoEntity {

    @ApiModelProperty(value = "系统唯一编码")
    private String uuid;
    @ApiModelProperty(value = "补货单商品唯一ID")
    private String replenishmentCommodityId;
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
    @ApiModelProperty(value = "签收数量")
    private int signInQuantity;
    @ApiModelProperty(value = "补货数量")
    private int replenishmentQuantity;
    @ApiModelProperty(value = "发货数量")
    private int deliveryNumber;
    @ApiModelProperty(value = "生成时间")
    private Timestamp createTime;
    @ApiModelProperty(value = "创建人")
    private String createUser;
    @ApiModelProperty(value = "最近修改人")
    private String modifyUser;
    @ApiModelProperty(value = "最近修改时间")
    private Timestamp modifyTime;
    @ApiModelProperty(value = "删除标识")
    private int deleteStatus;
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

    public int getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(int deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
