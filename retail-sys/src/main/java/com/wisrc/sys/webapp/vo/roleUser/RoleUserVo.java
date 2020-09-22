package com.wisrc.sys.webapp.vo.roleUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(description = "角色")
public class RoleUserVo {
    @ApiModelProperty(value = "角色编号", required = true)
    @NotEmpty
    private String roleId;

    @ApiModelProperty(value = "账号编号", required = true)
    @NotEmpty
    private String userId;

}
