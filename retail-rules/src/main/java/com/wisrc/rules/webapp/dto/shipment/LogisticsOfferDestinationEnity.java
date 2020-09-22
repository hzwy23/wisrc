package com.wisrc.rules.webapp.dto.shipment;

import io.swagger.annotations.ApiModelProperty;

public class LogisticsOfferDestinationEnity {
    @ApiModelProperty(value = "主键ID", hidden = true)
    private String uuid;
    @ApiModelProperty(value = "报价ID", hidden = true)
    private String offerId;
    @ApiModelProperty(value = "目的地编号")
    private int destinationCd;
    @ApiModelProperty(value = "状态，0--正常，1--已删除")
    private int deleteStatus;
    @ApiModelProperty(value = "目的地名称", hidden = true)
    private String destinationName;
    @ApiModelProperty(value = "物流商id", hidden = true)
    private String shipmentId;
    @ApiModelProperty(value = "渠道名称", hidden = true)
    private String channelName;

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public int getDestinationCd() {
        return destinationCd;
    }

    public void setDestinationCd(int destinationCd) {
        this.destinationCd = destinationCd;
    }

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


    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
