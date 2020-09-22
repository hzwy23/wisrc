package com.wisrc.sys.webapp.entity;

import java.util.List;
import java.util.Map;

/**
 * 账户用户信息
 */
public class AccountEntity {

    //SysUserInfoEntity部分
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
    private String employeeId;
    private String worktileId;

    //SysDeptInfoEntity部分
    private String deptCd;
    private String deptName;

    //
    private List<Map<String, Object>> roleList;

    private String positionName;

    private String password;
    private String confirmPassword;


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

    public List<Map<String, Object>> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Map<String, Object>> roleList) {
        this.roleList = roleList;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", statusCd=" + statusCd +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", qq=" + qq +
                ", weixin='" + weixin + '\'' +
                ", email='" + email + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", worktileId='" + worktileId + '\'' +
                ", deptCd='" + deptCd + '\'' +
                ", deptName='" + deptName + '\'' +
                ", roleList=" + roleList +
                ", positionName='" + positionName + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
