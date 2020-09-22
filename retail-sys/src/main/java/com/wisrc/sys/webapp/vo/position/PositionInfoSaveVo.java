package com.wisrc.sys.webapp.vo.position;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

@ApiModel(description = "岗位")
public class PositionInfoSaveVo extends PositionInfoVo {
    @ApiModelProperty(value = "岗位Id", required = true)
    @NotEmpty
    private String positionId;

    private Integer executiveDirectorAttr;

    @Override
    public Integer getExecutiveDirectorAttr() {
        return executiveDirectorAttr;
    }

    @Override
    public void setExecutiveDirectorAttr(Integer executiveDirectorAttr) {
        this.executiveDirectorAttr = executiveDirectorAttr;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }
}
