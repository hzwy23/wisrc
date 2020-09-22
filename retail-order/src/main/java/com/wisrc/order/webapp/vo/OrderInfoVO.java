package com.wisrc.order.webapp.vo;


import com.wisrc.order.webapp.entity.OrderLabelInfo;
import com.wisrc.order.webapp.entity.OrderRemarkInfoEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


public class OrderInfoVO {
    @ApiModelProperty(value = "订单号")
    private String orderId;
    @ApiModelProperty(value = "原始订单号")
    private String originalOrderId;
    @ApiModelProperty(value = "状态")
    private Integer statusCd;
    @ApiModelProperty(value = "平台")
    private String platId;
    @ApiModelProperty(value = "店铺")
    private String shopId;
    @ApiModelProperty(value = "订单总金额")
    private double amountMoney;
    @ApiModelProperty(value = "买家ID")
    private String customerId;
    @ApiModelProperty(value = "收货国家")
    private String countryCd;
    @ApiModelProperty(value = "物流商报价ID")
    private String offerId;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "发货备注")
    private String deliveryRemark;
    @ApiModelProperty(value = "标签列表")
    private String exceptTypeCd;
    @ApiModelProperty(value = "备注列表")
    private List<OrderRemarkInfoEntity> remarkInfoEntityList;
    @ApiModelProperty(value = "异常标签列表")
    private List<OrderLabelInfo> orderLabelInfoList;


    public String getExceptTypeCd() {
        return exceptTypeCd;
    }

    public void setExceptTypeCd(String exceptTypeCd) {
        this.exceptTypeCd = exceptTypeCd;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOriginalOrderId() {
        return originalOrderId;
    }

    public void setOriginalOrderId(String originalOrderId) {
        this.originalOrderId = originalOrderId;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    public String getPlatId() {
        return platId;
    }

    public void setPlatId(String platId) {
        this.platId = platId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public double getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(double amountMoney) {
        this.amountMoney = amountMoney;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCountryCd() {
        return countryCd;
    }

    public void setCountryCd(String countryCd) {
        this.countryCd = countryCd;
    }


    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDeliveryRemark() {
        return deliveryRemark;
    }

    public void setDeliveryRemark(String deliveryRemark) {
        this.deliveryRemark = deliveryRemark;
    }

    public List<OrderRemarkInfoEntity> getRemarkInfoEntityList() {
        return remarkInfoEntityList;
    }

    public void setRemarkInfoEntityList(List<OrderRemarkInfoEntity> remarkInfoEntityList) {
        this.remarkInfoEntityList = remarkInfoEntityList;
    }

    public List<OrderLabelInfo> getOrderLabelInfoList() {
        return orderLabelInfoList;
    }

    public void setOrderLabelInfoList(List<OrderLabelInfo> orderLabelInfoList) {
        this.orderLabelInfoList = orderLabelInfoList;
    }
}
