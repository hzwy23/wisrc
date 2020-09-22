package com.wisrc.warehouse.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 盘点单基本信息实体
 */
public class BlitemInfoEntity {
    @ApiModelProperty(value = "盘点单唯一id", hidden = true)
    private String blitemId;
    @ApiModelProperty(value = "盘点仓库编号")
    private String warehouseId;
    @ApiModelProperty("盘点日期")
    private String blitemDate;
    @ApiModelProperty("盘点状态")
    private Integer statusCd;
    @ApiModelProperty("操作人id")
    private String operationUser;
    @ApiModelProperty("操作时间")
    private String operationDate;
    @ApiModelProperty("审核人id")
    private String auditUser;
    @ApiModelProperty("审核时间")
    private String auditDate;
    @ApiModelProperty("删除标识状态")
    private Integer deleteStatus;
    @ApiModelProperty("盘点单创建时间")
    private String createTime;
    @ApiModelProperty("盘点单创建人ID")
    private String createUser;

    public String getBlitemId() {
        return blitemId;
    }

    public void setBlitemId(String blitemId) {
        this.blitemId = blitemId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getBlitemDate() {
        return blitemDate;
    }

    public void setBlitemDate(String blitemDate) {
        this.blitemDate = blitemDate;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    public String getOperationUser() {
        return operationUser;
    }

    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }


    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}

