package com.wisrc.sales.webapp.vo;

import com.wisrc.sales.webapp.enity.EstimateDetailEnity;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class EstimateEnityVo {
    @ApiModelProperty(value = "销售预估主键id", required = true)
    @NotEmpty(message = "销售预估主键id不能为空")
    private String estimateId;
    @ApiModelProperty(value = "更新人", required = false, hidden = true)
    private String updateUser;
    @ApiModelProperty(value = "更新人员工Id", required = false, hidden = true)
    private String updateEmployeeId;
    @ApiModelProperty(value = "销售预估详细列表", required = true)
    @NotEmpty(message = "销售预估详细不能为空")
    @Valid
    private List<EstimateDetailEnity> estimateDetailList;

    public String getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(String estimateId) {
        this.estimateId = estimateId;
    }

    public List<EstimateDetailEnity> getEstimateDetailList() {
        return estimateDetailList;
    }

    public void setEstimateDetailList(List<EstimateDetailEnity> estimateDetailList) {
        this.estimateDetailList = estimateDetailList;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateEmployeeId() {
        return updateEmployeeId;
    }

    public void setUpdateEmployeeId(String updateEmployeeId) {
        this.updateEmployeeId = updateEmployeeId;
    }
}
