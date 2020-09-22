package com.wisrc.sys.webapp.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;

@Data
public class RoleAndUserEntity {
    private String roleId;
    private String roleName;
    private Timestamp createTime;
    private String createUser;
    private Integer statusCd;
    private String userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleAndUserEntity that = (RoleAndUserEntity) o;
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
