package com.wisrc.sys.webapp.entity;

import java.util.Objects;

public class SysDeptInfoEntity {
    private String deptCd;
    private String deptName;
    private String parentCd;
    private Integer statusCd;
    private Integer deptTypeAttr;

    public String getDeptCd() {
        return deptCd;
    }

    public void setDeptCd(String deptCd) {
        this.deptCd = deptCd;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getParentCd() {
        return parentCd;
    }

    public void setParentCd(String parentCd) {
        this.parentCd = parentCd;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    public Integer getDeptTypeAttr() {
        return deptTypeAttr;
    }

    public void setDeptTypeAttr(Integer deptTypeAttr) {
        this.deptTypeAttr = deptTypeAttr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysDeptInfoEntity that = (SysDeptInfoEntity) o;
        return Objects.equals(deptCd, that.deptCd) &&
                Objects.equals(deptName, that.deptName) &&
                Objects.equals(parentCd, that.parentCd);
    }

    @Override
    public int hashCode() {

        return Objects.hash(deptCd, deptName, parentCd);
    }

    @Override
    public String toString() {
        return "SysDeptInfoEntity{" +
                "deptCd='" + deptCd + '\'' +
                ", deptName='" + deptName + '\'' +
                ", parentCd='" + parentCd + '\'' +
                ", statusCd=" + statusCd +
                ", deptTypeAttr=" + deptTypeAttr +
                '}';
    }
}
