package com.wisrc.crawler.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class RemoveOrderMskuEnity {
    @ApiModelProperty(value = "卖家ID")
    private String sellerId;
    @ApiModelProperty(value = "移除订单号")
    private String orderId;
    private String fnsku;
    private String requestedQuantity;
    private Integer shippedQuantity;
    private Integer cancelledQuantity;
    private Integer disposeDuantity;
    private Integer inProcessQuantity;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFnsku() {
        return fnsku;
    }

    public void setFnsku(String fnsku) {
        this.fnsku = fnsku;
    }

    public String getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(String requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public Integer getShippedQuantity() {
        return shippedQuantity;
    }

    public void setShippedQuantity(Integer shippedQuantity) {
        this.shippedQuantity = shippedQuantity;
    }

    public Integer getCancelledQuantity() {
        return cancelledQuantity;
    }

    public void setCancelledQuantity(Integer cancelledQuantity) {
        this.cancelledQuantity = cancelledQuantity;
    }

    public Integer getDisposeDuantity() {
        return disposeDuantity;
    }

    public void setDisposeDuantity(Integer disposeDuantity) {
        this.disposeDuantity = disposeDuantity;
    }

    public Integer getInProcessQuantity() {
        return inProcessQuantity;
    }

    public void setInProcessQuantity(Integer inProcessQuantity) {
        this.inProcessQuantity = inProcessQuantity;
    }
}
