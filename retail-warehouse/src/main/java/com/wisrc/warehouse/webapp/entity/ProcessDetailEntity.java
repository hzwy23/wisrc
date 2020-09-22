package com.wisrc.warehouse.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class ProcessDetailEntity {
    @ApiModelProperty(value = "唯一ID")
    private String uuid;
    @ApiModelProperty(value = "加工任务单ID")
    private String processTaskId;
    @ApiModelProperty(value = "库存Sku")
    private String skuId;
    @ApiModelProperty(value = "单位用量")
    private int unitNum;
    @ApiModelProperty(value = "总数量")
    private int totalAmount;
    @ApiModelProperty(value = "出库仓库Id")
    private String warehouseId;
    @ApiModelProperty(value = "批次")
    private String batch;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getProcessTaskId() {
        return processTaskId;
    }

    public void setProcessTaskId(String processTaskId) {
        this.processTaskId = processTaskId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
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

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}
