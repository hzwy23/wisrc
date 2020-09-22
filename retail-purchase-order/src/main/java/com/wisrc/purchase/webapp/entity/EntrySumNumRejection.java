package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(tags = "订单产品已经拒收的数量总和")
public class EntrySumNumRejection {
    @ApiModelProperty(value = "订单产品原来数量总和")
    private int quantity;
    @ApiModelProperty(value = "订单产品已拒收数量总和")
    private int sumRejection;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSumRejection() {
        return sumRejection;
    }

    public void setSumRejection(int sumRejection) {
        this.sumRejection = sumRejection;
    }
}
