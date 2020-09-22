package com.wisrc.warehouse.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class SelectProcessDetailVO {

    @ApiModelProperty(value = "库存sku")
    private String skuId;
    @ApiModelProperty(value = "库存产品中文名")
    private String productName;
    @ApiModelProperty(value = "单位用量")
    private int unitNum;
    @ApiModelProperty(value = "总数量")
    private int totalAmount;
    @ApiModelProperty(value = "出库仓库")
    private String warehouse_id;
    @ApiModelProperty(value = "批次")
    private String batch;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(int unitNum) {
        this.unitNum = unitNum;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(String warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}