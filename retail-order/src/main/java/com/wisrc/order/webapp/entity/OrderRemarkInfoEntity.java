package com.wisrc.order.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class OrderRemarkInfoEntity {
    @ApiModelProperty(value = "uuid", required = false, hidden = true)
    private String uuid;
    private String orderId;
    private String remark;
    @ApiModelProperty(value = "创建人", required = false, hidden = true)
    private String createUser;
    @ApiModelProperty(value = "创建时间", required = false, hidden = true)
    private String createTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
