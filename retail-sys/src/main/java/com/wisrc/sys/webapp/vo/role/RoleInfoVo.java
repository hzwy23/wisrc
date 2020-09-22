package com.wisrc.sys.webapp.vo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(description = "角色")
public class RoleInfoVo {
    @ApiModelProperty(value = "角色名称", required = true)
    @NotEmpty
    private String roleName;
}
