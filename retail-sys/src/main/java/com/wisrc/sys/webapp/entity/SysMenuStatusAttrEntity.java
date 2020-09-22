package com.wisrc.sys.webapp.entity;

public class SysMenuStatusAttrEntity {
    private int statusCd;
    private String statusDesc;

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

    @Override
    public String toString() {
        return "SysMenuStatusAttrEntity{" +
                "statusCd=" + statusCd +
                ", statusDesc='" + statusDesc + '\'' +
                '}';
    }
}
