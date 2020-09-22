package com.wisrc.order.webapp.vo;

import com.wisrc.order.webapp.entity.OrderLabelInfo;
import com.wisrc.order.webapp.entity.OrderRemarkInfoEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ExceptOrderVO {
    @ApiModelProperty(value = "订单号")
    private String orderId;
    @ApiModelProperty(value = "原始订单号")
    private String originalOrderId;
    @ApiModelProperty(value = "订单状态")
    private int statusCd;
    @ApiModelProperty(value = "平台ID")
    private String platId;
    @ApiModelProperty(value = "平台名称")
    private String platName;
    @ApiModelProperty(value = "店铺ID")
    private String shopId;
    @ApiModelProperty(value = "店铺名称")
    private String shopName;
    @ApiModelProperty(value = "订单总金额")
    private double totalAmount;
    @ApiModelProperty(value = "客户ID")
    private String customerId;
    @ApiModelProperty(value = "收货国家")
    private String countryCd;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "发货备注")
    private String deliveryRemark;
    @ApiModelProperty(value = "备注列表")
    private List<OrderRemarkInfoEntity> remarkInfoEntityList;
    @ApiModelProperty(value = "异常标签列表")
    private List<OrderLabelInfo> orderLabelInfoList;

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

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getPlatId() {
        return platId;
    }

    public void setPlatId(String platId) {
        this.platId = platId;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
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
