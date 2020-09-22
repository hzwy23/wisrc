package com.wisrc.sys.webapp.entity;

import java.util.Objects;

public class SysPositionInfoEntity {
    private String positionCd;
    private String positionName;
    private String parentCd;
    private String deptCd;
    private Integer statusCd;
    private Integer executiveDirectorAttr;

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    public String getPositionCd() {
        return positionCd;
    }

    public void setPositionCd(String positionCd) {
        this.positionCd = positionCd;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getParentCd() {
        return parentCd;
    }

    public void setParentCd(String parentCd) {
        this.parentCd = parentCd;
    }

    public String getDeptCd() {
        return deptCd;
    }

    public void setDeptCd(String deptCd) {
        this.deptCd = deptCd;
    }

    public Integer getExecutiveDirectorAttr() {
        return executiveDirectorAttr;
    }

    public void setExecutiveDirectorAttr(Integer executiveDirectorAttr) {
        this.executiveDirectorAttr = executiveDirectorAttr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysPositionInfoEntity that = (SysPositionInfoEntity) o;
        return Objects.equals(positionCd, that.positionCd) &&
                Objects.equals(positionName, that.positionName) &&
                Objects.equals(parentCd, that.parentCd) &&
                Objects.equals(deptCd, that.deptCd);
    }

    @Override
    public int hashCode() {

        return Objects.hash(positionCd, positionName, parentCd, deptCd);
    }
}
