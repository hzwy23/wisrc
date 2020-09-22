package com.wisrc.sys.webapp.entity;

import java.sql.Timestamp;
import java.util.Objects;

public class SysRoleInfoEntity {
    private String roleId;
    private String roleName;
    private Timestamp createTime;
    private String createUser;
    private Integer statusCd;

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

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysRoleInfoEntity that = (SysRoleInfoEntity) o;
        return Objects.equals(roleId, that.roleId) &&
                Objects.equals(roleName, that.roleName) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(createUser, that.createUser) &&
                Objects.equals(statusCd, that.statusCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName, createTime, createUser, statusCd);
    }
}
