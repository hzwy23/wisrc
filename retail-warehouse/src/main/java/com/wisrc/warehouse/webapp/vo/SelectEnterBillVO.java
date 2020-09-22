package com.wisrc.warehouse.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class SelectEnterBillVO {
    @ApiModelProperty(value = "入库单号")
    private String enterBillId;
    @ApiModelProperty(value = "仓库ID")
    private String warehouseId;
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;
    @ApiModelProperty(value = "入库类型编码")
    private int enterTypeCd;
    @ApiModelProperty(value = "入库类型名称")
    private String enterTypeName;
    @ApiModelProperty("手工入库单状态编码")
    private Integer statusCd;
    @ApiModelProperty("手工入库单状态名称")
    private String statusName;
    @ApiModelProperty(value = "创建人")
    private String createUser;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty("入库时间")
    private String enterWarehouseTime;
    @ApiModelProperty("取消原因")
    private String cancelReason;

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getEnterWarehouseTime() {
        return enterWarehouseTime;
    }

    public void setEnterWarehouseTime(String enterWarehouseTime) {
        this.enterWarehouseTime = enterWarehouseTime;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
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

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public int getEnterTypeCd() {
        return enterTypeCd;
    }

    public void setEnterTypeCd(int enterTypeCd) {
        this.enterTypeCd = enterTypeCd;
    }

    public String getEnterTypeName() {
        return enterTypeName;
    }

    public void setEnterTypeName(String enterTypeName) {
        this.enterTypeName = enterTypeName;
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
