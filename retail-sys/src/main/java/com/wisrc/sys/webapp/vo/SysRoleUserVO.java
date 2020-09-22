package com.wisrc.sys.webapp.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.Objects;

public class SysRoleUserVO {
    @JsonIgnore
    private String uuid;

    private String roleId;
    private String roleName;

    @JsonIgnore
    private String userId;

    @JsonIgnore
    private Timestamp createTime;

    @JsonIgnore
    private String createUser;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }


    @Override
    public int hashCode() {
        return Objects.hash(uuid, roleId, userId, createTime, createUser);
    }

    @Override
    public String toString() {
        return "SysRoleUserEntity{" +
                "uuid='" + uuid + '\'' +
                ", roleId='" + roleId + '\'' +
                ", userId='" + userId + '\'' +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}