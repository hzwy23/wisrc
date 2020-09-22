package com.wisrc.shipment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

public class ReturnWarehouseCheckEnity {
    @ApiModelProperty(value = "退仓申请单号", hidden = false)
    @NotEmpty(message = "退仓申请单号不能为空")
    private String returnApplyId;
    @ApiModelProperty(value = "更新时间", hidden = true)
    private String updateTime;
    @ApiModelProperty(value = "更新人", hidden = true)
    private String updateUser;
    @ApiModelProperty(value = "取消或未通过原因", hidden = false)
    private String reason;
    @ApiModelProperty(value = "申请单状态", hidden = false)
    private Integer statusCd;

    public String getReturnApplyId() {
        return returnApplyId;
    }

    public void setReturnApplyId(String returnApplyId) {
        this.returnApplyId = returnApplyId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }
}
