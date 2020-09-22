package com.wisrc.order.webapp.dto.warehouse;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

public class WarehouseManageInfoEntity {
    @ApiModelProperty(value = "仓库唯一编码")
    private String warehouseId;
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;
    @ApiModelProperty(value = "仓库类型(1--本地仓，2--海外仓，3--虚拟仓)")
    private int typeCd;
    @ApiModelProperty(value = "发货地址")
    private String shippAddress;
    @ApiModelProperty(value = "仓库状态（1--正常，2--停用）")
    private int statusCd;
    @ApiModelProperty(value = "操作人")
    private String createUser;
    @ApiModelProperty(value = "操作时间")
    private Timestamp createTime;

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getTypeCd() {
        return typeCd;
    }

    public void setTypeCd(int typeCd) {
        this.typeCd = typeCd;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getShippAddress() {
        return shippAddress;
    }

    public void setShippAddress(String shippAddress) {
        this.shippAddress = shippAddress;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "WarehouseManageInfoEntity{" +
                "warehouseId='" + warehouseId + '\'' +
                ", typeCd=" + typeCd +
                ", statusCd=" + statusCd +
                ", warehouseName='" + warehouseName + '\'' +
                ", shippAddress='" + shippAddress + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
