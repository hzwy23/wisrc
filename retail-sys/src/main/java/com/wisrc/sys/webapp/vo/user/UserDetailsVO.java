package com.wisrc.sys.webapp.vo.user;

import com.wisrc.sys.webapp.utils.Time;

import java.sql.Timestamp;

public class UserDetailsVO {
    private String weixin;
    private String qq;
    private String email;
    private String userId;
    private String userName;
    private String statusCd;
    private String createUser;
    private String createTime;
    private String phoneNumber;
    private String telephoneNumber;
    private String worktileId;
    private String employeeId;
    private String deptId;
    private String deptName;
    private String positionId;
    private String positionName;
    private Integer executiveDirectorAttr;

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
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

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getWorktileId() {
        return worktileId;
    }

    public void setWorktileId(String worktileId) {
        this.worktileId = worktileId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Integer getExecutiveDirectorAttr() {
        return executiveDirectorAttr;
    }

    public void setExecutiveDirectorAttr(Integer executiveDirectorAttr) {
        this.executiveDirectorAttr = executiveDirectorAttr;
    }

    @Override
    public String toString() {
        return "UserDetailsVO{" +
                "weixin='" + weixin + '\'' +
                ", qq='" + qq + '\'' +
                ", email='" + email + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", statusCd='" + statusCd + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime='" + createTime + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", worktileId='" + worktileId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", deptId='" + deptId + '\'' +
                ", deptName='" + deptName + '\'' +
                ", positionId='" + positionId + '\'' +
                ", positionName='" + positionName + '\'' +
                '}';
    }
}
