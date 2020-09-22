package com.wisrc.shipment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

public class LittlePackOfferDetailsEntity {

    @ApiModelProperty(value = "主键ID", hidden = true)
    private String uuid;
    @ApiModelProperty(value = "报价ID", hidden = true)
    private String offerId;
    @Min(value = 0, message = "开始区间只能为非负数")
    @ApiModelProperty(value = "开始区间")
    private Double startChargeSection;
    @ApiModelProperty(value = "结束区间")
    @Min(value = 0, message = "结束区间只能为非负数")
    private Double endChargeSection;
    @ApiModelProperty(value = "首重")
    @Min(value = 0, message = "首重只能为非负数")
    private int firstWeight;
    @ApiModelProperty(value = "首重价格")
    @Min(value = 0, message = "首重价格只能为非负数")
    private Double firstWeightPrice;
    @ApiModelProperty(value = "增加重量")
    @Min(value = 0, message = "增加重量只能为非负数")
    private int peerWeight;
    @ApiModelProperty(value = "附加价格")
    @Min(value = 0, message = "附加价格只能为非负数")
    private Double peerWeightPrice;
    @ApiModelProperty(value = "删除标识,0--正常，1--已删除")
    private int deleteStatus;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public Double getStartChargeSection() {
        return startChargeSection;
    }

    public void setStartChargeSection(Double startChargeSection) {
        this.startChargeSection = startChargeSection;
    }

    public Double getEndChargeSection() {
        return endChargeSection;
    }

    public void setEndChargeSection(Double endChargeSection) {
        this.endChargeSection = endChargeSection;
    }

    public int getFirstWeight() {
        return firstWeight;
    }

    public void setFirstWeight(int firstWeight) {
        this.firstWeight = firstWeight;
    }

    public Double getFirstWeightPrice() {
        return firstWeightPrice;
    }

    public void setFirstWeightPrice(Double firstWeightPrice) {
        this.firstWeightPrice = firstWeightPrice;
    }

    public int getPeerWeight() {
        return peerWeight;
    }

    public void setPeerWeight(int peerWeight) {
        this.peerWeight = peerWeight;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Double getPeerWeightPrice() {
        return peerWeightPrice;
    }

    public void setPeerWeightPrice(Double peerWeightPrice) {
        this.peerWeightPrice = peerWeightPrice;
    }

}
