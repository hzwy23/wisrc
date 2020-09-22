package com.wisrc.purchase.webapp.vo.orderProvision;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrderPdfProdictVO {
    @ApiModelProperty(value = "序列")
    private int num;
    @ApiModelProperty(value = "产品SKU")
    private String skuId;
    @ApiModelProperty(value = "产品名称")
    private String skuName;
    @ApiModelProperty(value = "产品型号")
    private String skuType;
    @ApiModelProperty(value = "数量")
    private int quantity;
    @ApiModelProperty(value = "备品率(%)")
    private double spareRate;
    @ApiModelProperty(value = "单位")
    private String unit;
    @ApiModelProperty(value = "不含税单价")
    private String unitPriceWithoutTax;
    @ApiModelProperty(value = "税率(%)")
    private double taxRate;
    @ApiModelProperty(value = "含税单价")
    private String unitPriceWithTax;
    @ApiModelProperty(value = "不含税金额")
    private String amountWithoutTax;
    @ApiModelProperty(value = "含税金额")
    private String amountWithTax;
    @ApiModelProperty(value = "装箱率")
    private String packingRate;
    @ApiModelProperty(value = "集装箱信息")
    private String producPackingInfoEn;
    @ApiModelProperty(value = "交货日期")
    private String deliveryTime;
    @ApiModelProperty(value = "备注")
    private String remark;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSkuType() {
        return skuType;
    }

    public void setSkuType(String skuType) {
        this.skuType = skuType;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
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

    public String getProducPackingInfoEn() {
        return producPackingInfoEn;
    }

    public void setProducPackingInfoEn(String producPackingInfoEn) {
        this.producPackingInfoEn = producPackingInfoEn;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
