package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;


public class EntryWarehouseEntity {
    @ApiModelProperty(value = "采购入库单号")
    private String entryId;
    @ApiModelProperty(value = "入库日期")
    private String entryTime;
    @ApiModelProperty(value = "入库人ID")
    private String entryUser;
    @ApiModelProperty(value = "入库ID")
    private String warehouseId;
    @ApiModelProperty(value = "提货单号")
    private String inspectionId;
    @ApiModelProperty(value = "供应商编号")
    private String supplierCd;
    @ApiModelProperty(value = "供应商送货单号")
    private String supplierDeliveryNum;
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;
    @ApiModelProperty(value = "创建人")
    private String createUser;
    @ApiModelProperty(value = "采购订单号")
    private String orderId;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "修改人")
    private String modifyUser;
    @ApiModelProperty(value = "修改时间")
    private Timestamp modifyTime;
    @ApiModelProperty(value = "删除标志")
    private int deleteStatus;

    @ApiModelProperty(value = "包材仓库")
    private String packWarehouseId;

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getEntryUser() {
        return entryUser;
    }

    public void setEntryUser(String entryUser) {
        this.entryUser = entryUser;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getSupplierCd() {
        return supplierCd;
    }

    public void setSupplierCd(String supplierCd) {
        this.supplierCd = supplierCd;
    }

    public String getSupplierDeliveryNum() {
        return supplierDeliveryNum;
    }

    public void setSupplierDeliveryNum(String supplierDeliveryNum) {
        this.supplierDeliveryNum = supplierDeliveryNum;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getPackWarehouseId() {
        return packWarehouseId;
    }

    public void setPackWarehouseId(String packWarehouseId) {
        this.packWarehouseId = packWarehouseId;
    }

    @Override
    public String toString() {
        return "EntryWarehouseEntity{" +
                "entryId='" + entryId + '\'' +
                ", entryTime=" + entryTime +
                ", entryUser='" + entryUser + '\'' +
                ", warehouseId='" + warehouseId + '\'' +
                ", inspectionId='" + inspectionId + '\'' +
                ", supplierCd='" + supplierCd + '\'' +
                ", supplierDeliveryNum='" + supplierDeliveryNum + '\'' +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", orderId='" + orderId + '\'' +
                ", remark='" + remark + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyTime=" + modifyTime +
                ", deleteStatus=" + deleteStatus +
                ", packWarehouseId='" + packWarehouseId + '\'' +
                '}';
    }
}
