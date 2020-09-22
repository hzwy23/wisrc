package com.wisrc.purchase.webapp.entity;

import java.util.Objects;

public class PlanStatusAttrEntity {
    private Integer statusCd;
    private String statusDesc;

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanStatusAttrEntity that = (PlanStatusAttrEntity) o;
        return Objects.equals(statusCd, that.statusCd) &&
                Objects.equals(statusDesc, that.statusDesc);
    }

    @Override
    public int hashCode() {

        return Objects.hash(statusCd, statusDesc);
    }
}
