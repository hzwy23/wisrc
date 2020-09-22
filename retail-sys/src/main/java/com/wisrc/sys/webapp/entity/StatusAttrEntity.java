package com.wisrc.sys.webapp.entity;

import java.util.Objects;

public class StatusAttrEntity {
    private int statusCd;
    private String statusDesc;
    private int type;

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusAttrEntity that = (StatusAttrEntity) o;
        return statusCd == that.statusCd &&
                type == that.type &&
                Objects.equals(statusDesc, that.statusDesc);
    }

    @Override
    public int hashCode() {

        return Objects.hash(statusCd, statusDesc, type);
    }
}
