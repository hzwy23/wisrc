package com.wisrc.quality.webapp.vo.inspectionApply;

import io.swagger.annotations.ApiModelProperty;

//外部接口调用入参实体
public class OrderIdListVO {

    @ApiModelProperty(value = "产品id")
    private String skuId;
    @ApiModelProperty(value = "采购订单id")
    private String orderId;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
