package com.wisrc.warehouse.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class EnableStockVO {
    @ApiModelProperty(value = "sku_id")
    private String skuId;
    @ApiModelProperty(value = "仓库Id")
    private String warehouseId;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }
}
