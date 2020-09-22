package com.wisrc.sys.webapp.vo.authorize;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(description = "角色")
public class AuthorizeUserRoleVo {
    @ApiModelProperty(value = "授权角色", required = true)
    @NotNull
    private List<String> roleIds;
}
