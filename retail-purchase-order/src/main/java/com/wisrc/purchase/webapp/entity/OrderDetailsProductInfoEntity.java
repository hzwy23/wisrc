package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(tags = "采购订单产品信息")
public class OrderDetailsProductInfoEntity {
    @ApiModelProperty(value = "采购订单产品ID")
    private String id;
    @ApiModelProperty(value = "采购订单号")
    private String orderId;
    @ApiModelProperty(value = "产品SKU")
    private String skuId;
    @ApiModelProperty(value = "数量")
    private int quantity;
    @ApiModelProperty(value = "备品率(%)")
    private double spareRate;
    @ApiModelProperty(value = "不含税单价")
    private double unitPriceWithoutTax;
    @ApiModelProperty(value = "税率(%)")
    private double taxRate;
    @ApiModelProperty(value = "含税单价")
    private double unitPriceWithTax;
    @ApiModelProperty(value = "不含税金额")
    private double amountWithoutTax;
    @ApiModelProperty(value = "含税金额")
    private double amountWithTax;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "是否中止")
    private int deleteStatus;

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSpareRate() {
        return spareRate;
    }

    public void setSpareRate(double spareRate) {
        this.spareRate = spareRate;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "OrderDetailsProductInfoEntity{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", skuId='" + skuId + '\'' +
                ", quantity=" + quantity +
                ", spareRate=" + spareRate +
                ", unitPriceWithoutTax=" + unitPriceWithoutTax +
                ", taxRate=" + taxRate +
                ", unitPriceWithTax=" + unitPriceWithTax +
                ", amountWithoutTax=" + amountWithoutTax +
                ", amountWithTax=" + amountWithTax +
                ", remark='" + remark + '\'' +
                '}';
    }
}
