package com.wisrc.sys.webapp.vo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel(description = "角色")
public class RoleInfoEditVo extends RoleInfoVo {
    @ApiModelProperty(value = "角色状态", required = true)
    @NotNull
    private Integer statusCd;

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }
}
