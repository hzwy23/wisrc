package com.wisrc.sys.webapp.entity;

import io.swagger.annotations.ApiModel;

import java.util.Objects;

@ApiModel
public class SysEmployeeInfoEntity {
    private String employeeId;
    private String employeeName;
    private Integer statusCd;
    private String positionCd;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysEmployeeInfoEntity that = (SysEmployeeInfoEntity) o;
        return Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(employeeName, that.employeeName) &&
                Objects.equals(statusCd, that.statusCd) &&
                Objects.equals(positionCd, that.positionCd);
    }

    @Override
    public int hashCode() {

        return Objects.hash(employeeId, employeeName, statusCd, positionCd);
    }

    @Override
    public String toString() {
        return "SysEmployeeInfoEntity{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", statusCd=" + statusCd +
                ", positionCd='" + positionCd + '\'' +
                '}';
    }
}
