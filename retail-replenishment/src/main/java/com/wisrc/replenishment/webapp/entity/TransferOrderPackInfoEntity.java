package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

public class TransferOrderPackInfoEntity {
    @ApiModelProperty("唯一标识")
    private String uuid;
    @ApiModelProperty("对应明细的唯一标识")
    private String commodityInfoCd;
    @ApiModelProperty("装箱长")
    private double outerBoxSpecificationLen;
    @ApiModelProperty("装箱宽")
    private double outerBoxSpecificationWidth;
    @ApiModelProperty("装箱高")
    private double outerBoxSpecificationHeight;
    @ApiModelProperty("装箱重量")
    private double weight;
    @ApiModelProperty("装箱量（pcs/箱）")
    private Integer packingQuantity;
    @ApiModelProperty("箱数")
    private Integer numberOfBoxes;
    @ApiModelProperty("删除标识")
    private Integer deleteStatus;
    @ApiModelProperty("是否为标准箱")
    private Integer isStandard;
    @ApiModelProperty("该装箱规格下的发货数量")
    private Integer deliveryQuantity;
    @ApiModelProperty("该装箱规格下的装箱数量")
    private Integer packQuantity;
    @ApiModelProperty("该装箱规格下的签收数量")
    private Integer signQuantity;
    @ApiModelProperty("改装箱规格下的调拨数量")
    private Integer transferQuantity;

    public Integer getDeliveryQuantity() {
        return deliveryQuantity;
    }

    public void setDeliveryQuantity(Integer deliveryQuantity) {
        this.deliveryQuantity = deliveryQuantity;
    }

    public Integer getPackQuantity() {
        return packQuantity;
    }

    public void setPackQuantity(Integer packQuantity) {
        this.packQuantity = packQuantity;
    }

    public Integer getSignQuantity() {
        return signQuantity;
    }

    public void setSignQuantity(Integer signQuantity) {
        this.signQuantity = signQuantity;
    }

    public Integer getTransferQuantity() {
        return transferQuantity;
    }

    public void setTransferQuantity(Integer transferQuantity) {
        this.transferQuantity = transferQuantity;
    }

    public Integer getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(Integer isStandard) {
        this.isStandard = isStandard;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCommodityInfoCd() {
        return commodityInfoCd;
    }

    public void setCommodityInfoCd(String commodityInfoCd) {
        this.commodityInfoCd = commodityInfoCd;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Integer getPackingQuantity() {
        return packingQuantity;
    }

    public void setPackingQuantity(Integer packingQuantity) {
        this.packingQuantity = packingQuantity;
    }

    public Integer getNumberOfBoxes() {
        return numberOfBoxes;
    }

    public void setNumberOfBoxes(Integer numberOfBoxes) {
        this.numberOfBoxes = numberOfBoxes;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
