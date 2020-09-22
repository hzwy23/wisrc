package com.wisrc.replenishment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class FbaStatusNumberVO {

    @ApiModelProperty(value = "待同步亚马逊数量")
    private int amazonNumber;
    @ApiModelProperty(value = "待装箱数量")
    private int packNumber;
    @ApiModelProperty(value = "待发货数量")
    private int deliveryNumber;
    @ApiModelProperty(value = "待签收数量")
    private int signNumber;
    @ApiModelProperty(value = "已签收数量")
    private int receiveNumber;
    @ApiModelProperty(value = "已取消数量")
    private int cancelNumber;
    @ApiModelProperty(value = "带选择渠道")
    private int channelNumber;

    public int getAmazonNumber() {
        return amazonNumber;
    }

    public void setAmazonNumber(int amazonNumber) {
        this.amazonNumber = amazonNumber;
    }

    public int getPackNumber() {
        return packNumber;
    }

    public void setPackNumber(int packNumber) {
        this.packNumber = packNumber;
    }

    public int getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(int deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public int getSignNumber() {
        return signNumber;
    }

    public void setSignNumber(int signNumber) {
        this.signNumber = signNumber;
    }

    public int getReceiveNumber() {
        return receiveNumber;
    }

    public void setReceiveNumber(int receiveNumber) {
        this.receiveNumber = receiveNumber;
    }

    public int getCancelNumber() {
        return cancelNumber;
    }

    public void setCancelNumber(int cancelNumber) {
        this.cancelNumber = cancelNumber;
    }

    public int getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(int channelNumber) {
        this.channelNumber = channelNumber;
    }
}
