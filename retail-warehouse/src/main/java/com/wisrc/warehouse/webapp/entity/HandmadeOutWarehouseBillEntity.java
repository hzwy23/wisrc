package com.wisrc.warehouse.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class HandmadeOutWarehouseBillEntity {
    @ApiModelProperty(value = "出库单编号", hidden = true)
    private String outBillId;
    @ApiModelProperty(value = "仓库Id")
    private String warehouseId;
    @ApiModelProperty(value = "分仓id")
    private String subWarehouseId;
    @ApiModelProperty(value = "出库单类型")
    private int outTypeCd;
    @ApiModelProperty(value = "创建人", hidden = true)
    private String createUser;
    @ApiModelProperty(value = "创建时间", hidden = true)
    private String createTime;

    public String getSubWarehouseId() {
        return subWarehouseId;
    }

    public void setSubWarehouseId(String subWarehouseId) {
        this.subWarehouseId = subWarehouseId;
    }

    public String getOutBillId() {
        return outBillId;
    }

    public void setOutBillId(String outBillId) {
        this.outBillId = outBillId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getOutTypeCd() {
        return outTypeCd;
    }

    public void setOutTypeCd(int outTypeCd) {
        this.outTypeCd = outTypeCd;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
