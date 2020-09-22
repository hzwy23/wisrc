package com.wisrc.sys.webapp.vo.role;

import com.wisrc.sys.webapp.vo.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "角色")
public class RoleInfoPageVo extends PageVo {
    @ApiModelProperty(value = "角色编号", required = false)
    private String roleId;

    @ApiModelProperty(value = "角色名称", required = false)
    private String roleName;

    @ApiModelProperty(value = "角色状态", required = false)
    private Integer statusCd;

    @ApiModelProperty(value = "账号编号", required = false)
    private String accountId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
