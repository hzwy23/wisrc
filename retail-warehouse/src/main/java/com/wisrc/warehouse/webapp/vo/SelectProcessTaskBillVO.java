package com.wisrc.warehouse.webapp.vo;


import io.swagger.annotations.ApiModelProperty;

public class SelectProcessTaskBillVO {

    @ApiModelProperty(value = "加工任务单号")
    private String processTaskId;
    @ApiModelProperty(value = "任务单状态编码")
    private int statusCd;
    @ApiModelProperty(value = "加工日期")
    private String processDate;
    @ApiModelProperty(value = "加工后库存SKU")
    private String processTaskSku;
    @ApiModelProperty(value = "产品中文名称")
    private String productName;
    @ApiModelProperty(value = "加工数量")
    private int processNum;
    @ApiModelProperty(value = "入库仓库")
    private String warehouseId;
    @ApiModelProperty(value = "批次")
    private String batch;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "创建人")
    private String createUser;


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

    public String getProcessTaskSku() {
        return processTaskSku;
    }

    public void setProcessTaskSku(String processTaskSku) {
        this.processTaskSku = processTaskSku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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


}



