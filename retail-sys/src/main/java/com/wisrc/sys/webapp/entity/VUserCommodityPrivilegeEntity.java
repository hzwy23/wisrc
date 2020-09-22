package com.wisrc.sys.webapp.entity;

public class VUserCommodityPrivilegeEntity {
    private String privilegeCd;
    private String privilegeName;
    private String commodityId;
    private String roleId;
    private String roleName;
    private String privilegeUserId;
    private String userId;
    private String employeeId;
    private String userName;
    private String employeeName;
    private String positionCd;
    private String positionName;
    private String deptCd;
    private Integer executiveDirectorAttr;

    public String getPrivilegeCd() {
        return privilegeCd;
    }

    public void setPrivilegeCd(String privilegeCd) {
        this.privilegeCd = privilegeCd;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPrivilegeUserId() {
        return privilegeUserId;
    }

    public void setPrivilegeUserId(String privilegeUserId) {
        this.privilegeUserId = privilegeUserId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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
    public String toString() {
        return "VUserCommodityPrivilegeEntity{" +
                "privileteCd='" + privilegeCd + '\'' +
                ", privilegeName='" + privilegeName + '\'' +
                ", commodityId='" + commodityId + '\'' +
                ", roleId='" + roleId + '\'' +
                ", roleName='" + roleName + '\'' +
                ", privilegeUserId='" + privilegeUserId + '\'' +
                ", userId='" + userId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", userName='" + userName + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", positionCd='" + positionCd + '\'' +
                ", positionName='" + positionName + '\'' +
                ", deptCd='" + deptCd + '\'' +
                ", executiveDirectorAttr=" + executiveDirectorAttr +
                '}';
    }
}
