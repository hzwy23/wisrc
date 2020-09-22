package com.wisrc.rules.webapp.entity;

import io.swagger.annotations.ApiModelProperty;


public class SaleLogisticsInfoEntity {
    @ApiModelProperty(value = "订单号")
    private String orderId;
    @ApiModelProperty(value = "物流商报价ID（只取小包报价物流商）")
    private String offerId;
    @ApiModelProperty(value = "运单号(实际是物流单号)")
    private String logisticsId;
    @ApiModelProperty(value = "物流费用")
    private String logisticsCost;
    @ApiModelProperty(value = "重量")
    private Double weight;
    @ApiModelProperty(value = "发货时间")
    private String deliveryDate;
    @ApiModelProperty(value = "发货备注")
    private String deliveryRemark;
    @ApiModelProperty(value = "买家自选提货渠道")
    private Integer deliveryChannelCd;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryRemark() {
        return deliveryRemark;
    }

    public void setDeliveryRemark(String deliveryRemark) {
        this.deliveryRemark = deliveryRemark;
    }

    public Integer getDeliveryChannelCd() {
        return deliveryChannelCd;
    }

    public void setDeliveryChannelCd(Integer deliveryChannelCd) {
        this.deliveryChannelCd = deliveryChannelCd;
    }
}
