package com.wisrc.order.webapp.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(tags = "拆分商品信息")
public class SplitOrderInfoVO {
    @ApiModelProperty(value = "商品ID")
    private String uuid;
    @ApiModelProperty(value = "商品数量")
    private int quantity;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
