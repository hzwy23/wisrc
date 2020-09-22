package com.wisrc.warehouse.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class HandmadeEnterWarehouseBillEntity {
    @ApiModelProperty(value = "入库单编号", hidden = true)
    private String enterBillId;
    @ApiModelProperty(value = "仓库Id")
    private String warehouseId;
    @ApiModelProperty(value = "分仓id")
    private String subWarehouseId;
    @ApiModelProperty(value = "入库单类型")
    private int enterTypeCd;
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

    public String getEnterBillId() {
        return enterBillId;
    }

    public void setEnterBillId(String enterBillId) {
        this.enterBillId = enterBillId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getEnterTypeCd() {
        return enterTypeCd;
    }

    public void setEnterTypeCd(int enterTypeCd) {
        this.enterTypeCd = enterTypeCd;
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
