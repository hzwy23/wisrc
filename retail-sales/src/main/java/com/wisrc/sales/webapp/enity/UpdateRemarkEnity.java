package com.wisrc.sales.webapp.enity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

public class UpdateRemarkEnity {
    @NotEmpty
    private String estimateDate;
    @NotEmpty
    private String estimateId;
    @NotEmpty(message = "备注不能为空")
    private String remark;
    @ApiModelProperty(value = "创建人", required = false, hidden = true)
    private String createUser;
    @ApiModelProperty(value = "员工Id", required = false, hidden = true)
    private String employeeId;
    @ApiModelProperty(value = "预估详细Id", required = false, hidden = true)
    private String estimateDetailId;
    @ApiModelProperty(value = "创建时间", required = false, hidden = true)
    private String createTime;

    public String getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(String estimateDate) {
        this.estimateDate = estimateDate;
    }

    public String getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(String estimateId) {
        this.estimateId = estimateId;
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEstimateDetailId() {
        return estimateDetailId;
    }

    public void setEstimateDetailId(String estimateDetailId) {
        this.estimateDetailId = estimateDetailId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
