package com.wisrc.warehouse.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class OutWarehouseRemarkEntity {
    @ApiModelProperty(value = "唯一ID", hidden = true)
    private String uuid;
    @ApiModelProperty(value = "出库单编号", hidden = true)
    private String outBillId;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "创建人", hidden = true)
    private String createUser;
    @ApiModelProperty(value = "创建时间", hidden = true)
    private String createTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOutBillId() {
        return outBillId;
    }

    public void setOutBillId(String outBillId) {
        this.outBillId = outBillId;
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
}
