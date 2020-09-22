package com.wisrc.sys.webapp.vo.authInfo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;


public class SetAuthInfoVO {
    @NotBlank(message = "数据权限编码authId不能为空")
    @ApiModelProperty(value = "数据权限编码", required = true)
    private String authId;//数据权限编码

    @NotBlank(message = "数据权限名称authName不能为空")
    @ApiModelProperty(value = "数据权限名称", required = true)
    private String authName;//数据权限名称

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }
}
