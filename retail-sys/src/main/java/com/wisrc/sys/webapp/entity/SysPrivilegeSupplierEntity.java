package com.wisrc.sys.webapp.entity;

import com.wisrc.sys.webapp.utils.Time;

import java.sql.Timestamp;
import java.util.Objects;

public class SysPrivilegeSupplierEntity {
    private String uuid;
    private String privilegeCd;
    private String supplierCd;
    private String createUser;
    private String createTime;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPrivilegeCd() {
        return privilegeCd;
    }

    public void setPrivilegeCd(String privilegeCd) {
        this.privilegeCd = privilegeCd;
    }

    public String getSupplierCd() {
        return supplierCd;
    }

    public void setSupplierCd(String supplierCd) {
        this.supplierCd = supplierCd;
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

    public void setCreateTime(Timestamp createTime) {
        this.createTime = Time.formatDateTime(createTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysPrivilegeSupplierEntity that = (SysPrivilegeSupplierEntity) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(privilegeCd, that.privilegeCd) &&
                Objects.equals(supplierCd, that.supplierCd);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, privilegeCd, supplierCd);
    }
}
