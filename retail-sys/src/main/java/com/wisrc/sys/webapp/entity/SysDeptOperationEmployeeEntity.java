package com.wisrc.sys.webapp.entity;

public class SysDeptOperationEmployeeEntity {
    private String deptCd;
    private String deptName;
    private String positionCd;
    private String positionName;
    private String employeeId;
    private String employeeName;
    private String statusCd;


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

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }


    @Override
    public String toString() {
        return "SysDeptOperationEmployeeEntity{" +
                "deptCd='" + deptCd + '\'' +
                ", deptName='" + deptName + '\'' +
                ", positionCd='" + positionCd + '\'' +
                ", positionName='" + positionName + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", statusCd='" + statusCd + '\'' +
                '}';
    }
}
