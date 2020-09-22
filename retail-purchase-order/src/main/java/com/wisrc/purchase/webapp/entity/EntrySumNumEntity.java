package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(tags = "订单产品已经入库的数量总和")
public class EntrySumNumEntity {
    @ApiModelProperty(value = "订单产品原来数量总和")
    private int quantity;
    @ApiModelProperty(value = "订单产品已入库数量总和")
    private int sumEntry;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSumEntry() {
        return sumEntry;
    }

    public void setSumEntry(int sumEntry) {
        this.sumEntry = sumEntry;
    }

}
