package com.wisrc.sys.webapp.entity;

import java.util.Objects;

public class SysUserStatusCdEntity {
    private int statusCd;
    private String statusCdDesc;

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getStatusCdDesc() {
        return statusCdDesc;
    }

    public void setStatusCdDesc(String statusCdDesc) {
        this.statusCdDesc = statusCdDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysUserStatusCdEntity that = (SysUserStatusCdEntity) o;
        return statusCd == that.statusCd &&
                Objects.equals(statusCdDesc, that.statusCdDesc);
    }

    @Override
    public int hashCode() {

        return Objects.hash(statusCd, statusCdDesc);
    }
}
