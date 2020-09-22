package com.wisrc.warehouse.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class WarehouseOutEnterInfoEntity {

    @ApiModelProperty(value = "仓库ID")
    private String warehouseId;
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;
    @ApiModelProperty(value = "来源ID")
    private String sourceId;
    @ApiModelProperty(value = "skuId")
    private String skuId;
    @ApiModelProperty(value = "产品名称")
    private String skuName;
    @ApiModelProperty(value = "fnSkuId")
    private String fnSkuId;
    @ApiModelProperty(value = "生产批次")
    private String productionBatch;
    @ApiModelProperty(value = "入库批次")
    private String enterBatch;
    @ApiModelProperty(value = "变更前数量")
    private int changeAgoNum;
    @ApiModelProperty(value = "出入库类型")
    private int outEnterType;
    @ApiModelProperty(value = "变更数量")
    private int changeNum;
    @ApiModelProperty(value = "变更后数量")
    private int changeLaterNum;
    @ApiModelProperty(value = "单据类型")
    private String documentType;
    @ApiModelProperty(value = "库位")
    private String warehousePosition;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "创建人")
    private String createUser;


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

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getFnSkuId() {
        return fnSkuId;
    }

    public void setFnSkuId(String fnSkuId) {
        this.fnSkuId = fnSkuId;
    }

    public String getProductionBatch() {
        return productionBatch;
    }

    public void setProductionBatch(String productionBatch) {
        this.productionBatch = productionBatch;
    }

    public String getEnterBatch() {
        return enterBatch;
    }

    public void setEnterBatch(String enterBatch) {
        this.enterBatch = enterBatch;
    }

    public int getChangeAgoNum() {
        return changeAgoNum;
    }

    public void setChangeAgoNum(int changeAgoNum) {
        this.changeAgoNum = changeAgoNum;
    }

    public int getOutEnterType() {
        return outEnterType;
    }

    public void setOutEnterType(int outEnterType) {
        this.outEnterType = outEnterType;
    }

    public int getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(int changeNum) {
        this.changeNum = changeNum;
    }

    public int getChangeLaterNum() {
        return changeLaterNum;
    }

    public void setChangeLaterNum(int changeLaterNum) {
        this.changeLaterNum = changeLaterNum;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getWarehousePosition() {
        return warehousePosition;
    }

    public void setWarehousePosition(String warehousePosition) {
        this.warehousePosition = warehousePosition;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}
