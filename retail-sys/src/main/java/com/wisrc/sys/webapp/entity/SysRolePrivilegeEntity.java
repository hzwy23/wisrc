package com.wisrc.sys.webapp.entity;

public class SysRolePrivilegeEntity {
    private String uuid;
    private String userId;
    private String roleId;
    private String privilegeCd;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPrivilegeCd() {
        return privilegeCd;
    }

    public void setPrivilegeCd(String privilegeCd) {
        this.privilegeCd = privilegeCd;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "SysRolePrivilegeEntity{" +
                "uuid='" + uuid + '\'' +
                ", userId='" + userId + '\'' +
                ", roleId='" + roleId + '\'' +
                ", privilegeCd='" + privilegeCd + '\'' +
                '}';
    }
}
