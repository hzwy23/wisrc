package com.wisrc.sales.webapp.enity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;
import java.sql.Timestamp;

@Data
public class RemarkEnity {
    @NotEmpty(message = "备注不允许为空")
    private String remark;
    @NotEmpty(message = "commodityId不允许为空")
    private String commodityId;
    @NotEmpty
    private String estimateDate;
    @ApiModelProperty(value = "创建人", required = false, hidden = true)
    private String createUser;
    @ApiModelProperty(value = "员工号", required = false, hidden = true)
    private String employeeId;
    @ApiModelProperty(value = "预估详细Id", required = false, hidden = true)
    private String estimateDetailId;
    @ApiModelProperty(value = "创建时间", required = false, hidden = true)
    private String createTime;

    @ApiModelProperty(value = "更新标识", required = false, hidden = true)
    private Integer updateFlag;
    @ApiModelProperty(value = "关联条件", required = false, hidden = true)
    private String estimateId;
    // 生效日期
    private Timestamp effectiveDate;
    // 时效日期
    private Timestamp expirationDate;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
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

    public String getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(String estimateDate) {
        this.estimateDate = estimateDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
