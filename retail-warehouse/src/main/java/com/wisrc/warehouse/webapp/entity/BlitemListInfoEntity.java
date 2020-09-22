package com.wisrc.warehouse.webapp.entity;

import io.swagger.annotations.ApiModelProperty;


/**
 * 盘点清单基本信息实体
 */
public class BlitemListInfoEntity {
    @ApiModelProperty(value = "盘点清单唯一标识", hidden = true)
    private String uuid;
    @ApiModelProperty("该清单对应的盘点单号")
    private String blitemId;
    @ApiModelProperty("库存SKUid")
    private String skuId;
    @ApiModelProperty("盘点仓库地址编码")
    private String warehousePosition;
    @ApiModelProperty("盘点前库存")
    private Integer auditStockAgo;
    @ApiModelProperty("盘点后的库存")
    private Integer auditStockBack;
    @ApiModelProperty("盘点差异原因")
    private String diversityReason;
    @ApiModelProperty("删除标识")
    private Integer deleteStatus;
    @ApiModelProperty("创建人id")
    private String createUser;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("修改时间")
    private String modifyTime;
    @ApiModelProperty("修改人编号")
    private String modifyUser;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getBlitemId() {
        return blitemId;
    }

    public void setBlitemId(String blitemId) {
        this.blitemId = blitemId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getWarehousePosition() {
        return warehousePosition;
    }

    public void setWarehousePosition(String warehousePosition) {
        this.warehousePosition = warehousePosition;
    }

    public Integer getAuditStockAgo() {
        return auditStockAgo;
    }

    public void setAuditStockAgo(Integer auditStockAgo) {
        this.auditStockAgo = auditStockAgo;
    }

    public Integer getAuditStockBack() {
        return auditStockBack;
    }

    public void setAuditStockBack(Integer auditStockBack) {
        this.auditStockBack = auditStockBack;
    }

    public String getDiversityReason() {
        return diversityReason;
    }

    public void setDiversityReason(String diversityReason) {
        this.diversityReason = diversityReason;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
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

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }
}
