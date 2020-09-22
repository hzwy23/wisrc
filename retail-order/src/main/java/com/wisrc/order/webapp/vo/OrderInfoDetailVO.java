package com.wisrc.order.webapp.vo;

import com.wisrc.order.webapp.entity.LogisticsCostInfoEntity;
import com.wisrc.order.webapp.entity.OrderBasicInfoEntity;
import com.wisrc.order.webapp.entity.OrderCommodityInfoEntity;
import com.wisrc.order.webapp.entity.OrderCustomerInfoEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class OrderInfoDetailVO {
    @ApiModelProperty(value = "订单号")
    private String orderId;
    private OrderBasicInfoEntity orderInfoEntity;
    private OrderCustomerInfoEntity customerInfoEntity;//客户信息
    private LogisticsCostInfoEntity logisticsInfoEntity;//物流信息
    private List<OrderCommodityInfoEntity> commodityInfoEntityList;//商品信息
    //private List<SaleOrderCommodityInfoVO>  commodityInfoVOList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderBasicInfoEntity getOrderInfoEntity() {
        return orderInfoEntity;
    }

    public void setOrderInfoEntity(OrderBasicInfoEntity orderInfoEntity) {
        this.orderInfoEntity = orderInfoEntity;
    }

    public OrderCustomerInfoEntity getCustomerInfoEntity() {
        return customerInfoEntity;
    }

    public void setCustomerInfoEntity(OrderCustomerInfoEntity customerInfoEntity) {
        this.customerInfoEntity = customerInfoEntity;
    }

    public LogisticsCostInfoEntity getLogisticsInfoEntity() {
        return logisticsInfoEntity;
    }

    public void setLogisticsInfoEntity(LogisticsCostInfoEntity logisticsInfoEntity) {
        this.logisticsInfoEntity = logisticsInfoEntity;
    }

    public List<OrderCommodityInfoEntity> getCommodityInfoEntityList() {
        return commodityInfoEntityList;
    }

    public void setCommodityInfoEntityList(List<OrderCommodityInfoEntity> commodityInfoEntityList) {
        this.commodityInfoEntityList = commodityInfoEntityList;
    }

}
