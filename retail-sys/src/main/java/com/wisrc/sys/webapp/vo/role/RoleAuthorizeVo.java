package com.wisrc.sys.webapp.vo.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "角色")
public class RoleAuthorizeVo {
    @ApiModelProperty(value = "新增菜单", required = true)
    private List<String> addMenusId;

    @ApiModelProperty(value = "删除菜单", required = true)
    private List<String> delMenusId;
}
