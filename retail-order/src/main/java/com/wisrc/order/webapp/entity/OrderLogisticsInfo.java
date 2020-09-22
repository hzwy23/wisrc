package com.wisrc.order.webapp.entity;


import io.swagger.annotations.ApiModelProperty;

public class OrderLogisticsInfo {
    private String orderId;
    @ApiModelProperty(value = "买家自选物流渠道", required = false)
    private String deliveryChannelCd;
    @ApiModelProperty(value = "发货物流渠道", required = false)
    private String offerId;
    @ApiModelProperty(value = "物流单号", required = false)
    private String logisticsId;
    @ApiModelProperty(value = "物流费用", required = false)
    private String logisticsCost;
    @ApiModelProperty(value = "重量", required = false)
    private Double weight;
    @ApiModelProperty(value = "发货日期", required = false)
    private String deliveryDate;
    @ApiModelProperty(value = "发货备注", required = false)
    private String deliveryRemark;
    @ApiModelProperty(value = "物流商id", required = false)
    private String shipMentId;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDeliveryChannelCd() {
        return deliveryChannelCd;
    }

    public void setDeliveryChannelCd(String deliveryChannelCd) {
        this.deliveryChannelCd = deliveryChannelCd;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getLogisticsCost() {
        return logisticsCost;
    }

    public void setLogisticsCost(String logisticsCost) {
        this.logisticsCost = logisticsCost;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }


    public String getDeliveryRemark() {
        return deliveryRemark;
    }

    public void setDeliveryRemark(String deliveryRemark) {
        this.deliveryRemark = deliveryRemark;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getShipMentId() {
        return shipMentId;
    }

    public void setShipMentId(String shipMentId) {
        this.shipMentId = shipMentId;
    }

    @Override
    public String toString() {
        return "OrderLogisticsInfo{" +
                "orderId='" + orderId + '\'' +
                ", deliveryChannelCd=" + deliveryChannelCd +
                ", offerId='" + offerId + '\'' +
                ", logisticsId='" + logisticsId + '\'' +
                ", logisticsCost='" + logisticsCost + '\'' +
                ", weight=" + weight +
                ", deliveryDate=" + deliveryDate +
                ", deliveryRemark='" + deliveryRemark + '\'' +
                '}';
    }
}
