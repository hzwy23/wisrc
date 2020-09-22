package com.wisrc.order.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class ProductEnityVo {
    @ApiModelProperty(value = "商品id")
    private String commodityId;
    @ApiModelProperty(value = "仓库id")
    private String warehouseId;
    @ApiModelProperty(value = "商品属性")
    private String attributeDesc;
    @ApiModelProperty(value = "商品单价")
    private double unitPrice;
    @ApiModelProperty(value = "单价币种")
    private String unitPriceCurrency;
    @ApiModelProperty(value = "商品数量")
    private int quantity;
    @ApiModelProperty(value = "库存SKU", required = false, hidden = true)
    private String skuId;
    @ApiModelProperty(value = "重量", required = false, hidden = true)
    private Double weight;
    @ApiModelProperty(value = "产品中文名", required = false, hidden = true)
    private String mskuName;

    public String getMskuName() {
        return mskuName;
    }

    public void setMskuName(String mskuName) {
        this.mskuName = mskuName;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getAttributeDesc() {
        return attributeDesc;
    }

    public void setAttributeDesc(String attributeDesc) {
        this.attributeDesc = attributeDesc;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnitPriceCurrency() {
        return unitPriceCurrency;
    }

    public void setUnitPriceCurrency(String unitPriceCurrency) {
        this.unitPriceCurrency = unitPriceCurrency;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
