package com.wisrc.sys.webapp.entity;

import com.wisrc.sys.webapp.utils.Time;

import java.sql.Timestamp;
import java.util.Objects;

public class SysPrivilegeWarehouseEntity {
    private String uuid;
    private String warehouseCd;
    private String privilegeCd;
    private String createUser;
    private String createTime;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getWarehouseCd() {
        return warehouseCd;
    }

    public void setWarehouseCd(String warehouseCd) {
        this.warehouseCd = warehouseCd;
    }

    public String getPrivilegeCd() {
        return privilegeCd;
    }

    public void setPrivilegeCd(String privilegeCd) {
        this.privilegeCd = privilegeCd;
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
        SysPrivilegeWarehouseEntity that = (SysPrivilegeWarehouseEntity) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(warehouseCd, that.warehouseCd) &&
                Objects.equals(privilegeCd, that.privilegeCd);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, warehouseCd, privilegeCd);
    }

    @Override
    public String toString() {
        return "SysPrivilegeWarehouseEntity{" +
                "uuid='" + uuid + '\'' +
                ", warehouseCd='" + warehouseCd + '\'' +
                ", privilegeCd='" + privilegeCd + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
