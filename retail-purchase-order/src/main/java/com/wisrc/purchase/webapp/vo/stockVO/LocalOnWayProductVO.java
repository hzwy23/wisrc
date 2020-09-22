package com.wisrc.purchase.webapp.vo.stockVO;

import io.swagger.annotations.ApiModelProperty;

public class LocalOnWayProductVO {
    @ApiModelProperty(value = "采购单号")
    private String orderId;
    @ApiModelProperty(value = "采购数量")
    private int quantity;
    @ApiModelProperty(value = "计划交货时间")
    private String deliveryTime;
    @ApiModelProperty(value = "计划交货数量")
    private int number;
    @ApiModelProperty(value = "已交货数量")
    private int entryNum;
    @ApiModelProperty(value = "未交货数量")
    private int unpaidQuantity;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getEntryNum() {
        return entryNum;
    }

    public void setEntryNum(int entryNum) {
        this.entryNum = entryNum;
    }

    public int getUnpaidQuantity() {
        return unpaidQuantity;
    }

    public void setUnpaidQuantity(int unpaidQuantity) {
        this.unpaidQuantity = unpaidQuantity;
    }
}
