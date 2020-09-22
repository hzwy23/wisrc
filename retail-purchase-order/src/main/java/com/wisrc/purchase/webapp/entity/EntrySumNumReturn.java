package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(tags = "订单产品已经退货的数量总和")
public class EntrySumNumReturn {
    @ApiModelProperty(value = "订单产品原来数量总和")
    private int quantity;
    @ApiModelProperty(value = "订单产品已退货数量总和")
    private int sumReturn;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSumReturn() {
        return sumReturn;
    }

    public void setSumReturn(int sumReturn) {
        this.sumReturn = sumReturn;
    }
}
