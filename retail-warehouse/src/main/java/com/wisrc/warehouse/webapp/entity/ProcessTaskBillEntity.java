package com.wisrc.warehouse.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class ProcessTaskBillEntity {
    @ApiModelProperty(value = "加工任务单号")
    private String processTaskId;
    @ApiModelProperty(value = "加工任务单状态编码")
    private int statusCd;
    @ApiModelProperty(value = "加工日期")
    private String processDate;
    @ApiModelProperty(value = "加工后库存SKU")
    private String processLaterSku;
    @ApiModelProperty(value = "加工数量")
    private int processNum;
    @ApiModelProperty(value = "入库仓库Id")
    private String warehouseId;
    @ApiModelProperty(value = "批次")
    private String batch;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "创建人")
    private String createUser;
    @ApiModelProperty(value = "创建日期")
    private String createTime;
    //随机数
    private String randomValue;
    @ApiModelProperty("这个加工任务单对应的补货单号")
    private String fbaReplenishmentId;

    public String getRandomValue() {
        return randomValue;
    }

    public void setRandomValue(String randomValue) {
        this.randomValue = randomValue;
    }

    public String getProcessTaskId() {
        return processTaskId;
    }

    public void setProcessTaskId(String processTaskId) {
        this.processTaskId = processTaskId;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getProcessDate() {
        return processDate;
    }

    public void setProcessDate(String processDate) {
        this.processDate = processDate;
    }

    public String getProcessLaterSku() {
        return processLaterSku;
    }

    public void setProcessLaterSku(String processLaterSku) {
        this.processLaterSku = processLaterSku;
    }

    public int getProcessNum() {
        return processNum;
    }

    public void setProcessNum(int processNum) {
        this.processNum = processNum;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getFbaReplenishmentId() {
        return fbaReplenishmentId;
    }

    public void setFbaReplenishmentId(String fbaReplenishmentId) {
        this.fbaReplenishmentId = fbaReplenishmentId;
    }
}
