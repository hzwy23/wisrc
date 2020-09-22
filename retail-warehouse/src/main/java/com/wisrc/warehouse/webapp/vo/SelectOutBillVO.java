package com.wisrc.warehouse.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class SelectOutBillVO {
    @ApiModelProperty(value = "出库单号")
    private String outBillId;
    @ApiModelProperty(value = "仓库ID")
    private String warehouseId;
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;
    @ApiModelProperty(value = "出库类型编码")
    private int outTypeCd;
    @ApiModelProperty(value = "出库类型名称")
    private int outTypeName;
    @ApiModelProperty(value = "操作人")
    private String createUser;
    @ApiModelProperty(value = "操作时间")
    private String createTime;

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

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public int getOutTypeCd() {
        return outTypeCd;
    }

    public void setOutTypeCd(int outTypeCd) {
        this.outTypeCd = outTypeCd;
    }

    public int getOutTypeName() {
        return outTypeName;
    }

    public void setOutTypeName(int outTypeName) {
        this.outTypeName = outTypeName;
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
