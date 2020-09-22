package com.wisrc.sys.webapp.entity;

import com.wisrc.sys.webapp.utils.Time;
import com.wisrc.sys.webapp.vo.SysRoleUserVO;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class SysUserInfoEntity {
    private String userId;
    private String userName;
    private Integer statusCd;
    private String createUser;
    private String createTime;
    private String phoneNumber;
    private Integer qq;
    private String weixin;
    private String email;
    private String telephoneNumber;
    private String worktileId;

    private String employeeId;
    private String employeeName;
    private String employeeStatusCd;

    private String positionId;
    private String positionName;
    private String positionParentCd;
    private String positionStatusCd;

    private String departmentId;
    private String deptName;
    private String deptParentCd;
    private String deptStatusCd;

    private Integer deptTypeAttr;
    private Integer executiveDirectorAttr;

    private List<SysRoleUserVO> roleList;

    public List<SysRoleUserVO> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRoleUserVO> roleList) {
        this.roleList = roleList;
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

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
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

    public Integer getQq() {
        return qq;
    }

    public void setQq(Integer qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getWorktileId() {
        return worktileId;
    }

    public void setWorktileId(String worktileId) {
        this.worktileId = worktileId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeStatusCd() {
        return employeeStatusCd;
    }

    public void setEmployeeStatusCd(String employeeStatusCd) {
        this.employeeStatusCd = employeeStatusCd;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionParentCd() {
        return positionParentCd;
    }

    public void setPositionParentCd(String positionParentCd) {
        this.positionParentCd = positionParentCd;
    }

    public String getPositionStatusCd() {
        return positionStatusCd;
    }

    public void setPositionStatusCd(String positionStatusCd) {
        this.positionStatusCd = positionStatusCd;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptParentCd() {
        return deptParentCd;
    }

    public void setDeptParentCd(String deptParentCd) {
        this.deptParentCd = deptParentCd;
    }

    public String getDeptStatusCd() {
        return deptStatusCd;
    }

    public void setDeptStatusCd(String deptStatusCd) {
        this.deptStatusCd = deptStatusCd;
    }

    public Integer getDeptTypeAttr() {
        return deptTypeAttr;
    }

    public void setDeptTypeAttr(Integer deptTypeAttr) {
        this.deptTypeAttr = deptTypeAttr;
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
        SysUserInfoEntity that = (SysUserInfoEntity) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(statusCd, that.statusCd) &&
                Objects.equals(createUser, that.createUser) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(qq, that.qq) &&
                Objects.equals(weixin, that.weixin) &&
                Objects.equals(email, that.email) &&
                Objects.equals(telephoneNumber, that.telephoneNumber) &&
                Objects.equals(worktileId, that.worktileId) &&
                Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(employeeName, that.employeeName) &&
                Objects.equals(employeeStatusCd, that.employeeStatusCd) &&
                Objects.equals(positionId, that.positionId) &&
                Objects.equals(positionName, that.positionName) &&
                Objects.equals(positionParentCd, that.positionParentCd) &&
                Objects.equals(positionStatusCd, that.positionStatusCd) &&
                Objects.equals(departmentId, that.departmentId) &&
                Objects.equals(deptName, that.deptName) &&
                Objects.equals(deptParentCd, that.deptParentCd) &&
                Objects.equals(deptStatusCd, that.deptStatusCd) &&
                Objects.equals(deptTypeAttr, that.deptTypeAttr) &&
                Objects.equals(executiveDirectorAttr, that.executiveDirectorAttr) &&
                Objects.equals(roleList, that.roleList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, userName, statusCd, createUser, createTime, phoneNumber, qq, weixin, email, telephoneNumber, worktileId, employeeId, employeeName, employeeStatusCd, positionId, positionName, positionParentCd, positionStatusCd, departmentId, deptName, deptParentCd, deptStatusCd, deptTypeAttr, executiveDirectorAttr, roleList);
    }

    @Override
    public String toString() {
        return "SysUserInfoEntity{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", statusCd=" + statusCd +
                ", createUser='" + createUser + '\'' +
                ", createTime='" + createTime + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", qq=" + qq +
                ", weixin='" + weixin + '\'' +
                ", email='" + email + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", worktileId='" + worktileId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", employeeStatusCd='" + employeeStatusCd + '\'' +
                ", positionId='" + positionId + '\'' +
                ", positionName='" + positionName + '\'' +
                ", positionParentCd='" + positionParentCd + '\'' +
                ", positionStatusCd='" + positionStatusCd + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", deptName='" + deptName + '\'' +
                ", deptParentCd='" + deptParentCd + '\'' +
                ", deptStatusCd='" + deptStatusCd + '\'' +
                ", deptTypeAttr='" + deptTypeAttr + '\'' +
                ", executiveDirectorAttr='" + executiveDirectorAttr + '\'' +
                ", roleList=" + roleList +
                '}';
    }
}
