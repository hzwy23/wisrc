package com.wisrc.sys.webapp.vo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "角色开关")
public class RoleSwitchVo {
    @ApiModelProperty(value = "角色状态", required = true)
    @NotNull
    private Integer statusCd;
}
