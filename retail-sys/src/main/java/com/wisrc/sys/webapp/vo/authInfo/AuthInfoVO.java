package com.wisrc.sys.webapp.vo.authInfo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;


public class AuthInfoVO {
    private String authId;//数据权限编码
    private String authName;//数据权限名称
    private Integer privilegeTypeAttr;

    @ApiModelProperty(value = "创建者", hidden = true)
    private String createUser;
    @ApiParam(hidden = true)
    private String createTime;

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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getPrivilegeTypeAttr() {
        return privilegeTypeAttr;
    }

    public void setPrivilegeTypeAttr(Integer privilegesTypeAttr) {
        this.privilegeTypeAttr = privilegesTypeAttr;
    }

    @Override
    public String toString() {
        return "AuthInfoVO{" +
                "authId='" + authId + '\'' +
                ", authName='" + authName + '\'' +
                ", privilegesTypeAttr=" + privilegeTypeAttr +
                ", createUser='" + createUser + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
