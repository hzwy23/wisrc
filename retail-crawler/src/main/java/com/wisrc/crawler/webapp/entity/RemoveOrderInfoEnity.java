package com.wisrc.crawler.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class RemoveOrderInfoEnity {
    @ApiModelProperty(value = "卖家ID")
    private String sellerId;
    @ApiModelProperty(value = "移除订单号")
    private String orderId;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "订单状态")
    private String orderStatus;
    @ApiModelProperty(value = "申请移除数量")
    private Integer applyRemoveNum;
    @ApiModelProperty(value = "移除订单明细")
    private List<RemoveOrderDetailEnity> removeOrderShipmentInfoList;
    @ApiModelProperty(value = "移除订单明细")
    private List<RemoveOrderMskuEnity> removeMskuInfoList;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


    public List getRemoveOrderShipmentInfoList() {
        return removeOrderShipmentInfoList;
    }

    public void setRemoveOrderShipmentInfoList(List removeOrderShipmentInfoList) {
        this.removeOrderShipmentInfoList = removeOrderShipmentInfoList;
    }

    public Integer getApplyRemoveNum() {
        return applyRemoveNum;
    }

    public void setApplyRemoveNum(Integer applyRemoveNum) {
        this.applyRemoveNum = applyRemoveNum;
    }

    public List<RemoveOrderMskuEnity> getRemoveMskuInfoList() {
        return removeMskuInfoList;
    }

    public void setRemoveMskuInfoList(List<RemoveOrderMskuEnity> removeMskuInfoList) {
        this.removeMskuInfoList = removeMskuInfoList;
    }
}
