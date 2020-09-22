package com.wisrc.order.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class SendOrderInfoVo {

    @ApiModelProperty(value = "订单号")
    private String orderId;
    @ApiModelProperty(value = "原始订单号")
    private String originalOrderId;
    @ApiModelProperty(value = "发货渠道id", required = false, hidden = true)
    private String offerId;
    @ApiModelProperty(value = "订单商品详情", required = false, hidden = true)
    private List<ProductEnityVo> productList;

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

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public List<ProductEnityVo> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductEnityVo> productList) {
        this.productList = productList;
    }
}
