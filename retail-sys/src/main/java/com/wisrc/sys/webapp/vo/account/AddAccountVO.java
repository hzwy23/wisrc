package com.wisrc.sys.webapp.vo.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@ApiModel
public class AddAccountVO {
    //SysUserInfoEntity部分
    @NotBlank(message = "账户userId不能为空")
    @ApiModelProperty(value = "账户ID", required = true)
    private String userId;

    @NotBlank(message = "账户名userName不能为空")
    @ApiModelProperty(value = "账户名", required = true)
    private String userName;

    @ApiModelProperty(value = "手机号码", required = false)
    private String phoneNumber;

    @ApiModelProperty(value = "QQ", required = false)
    private Integer qq;

    @ApiModelProperty(value = "微信", required = false)
    private String weixin;

    @ApiModelProperty(value = "电子邮箱", required = false)
    private String email;

    @ApiModelProperty(value = "座机号码", required = false)
    private String telephoneNumber;

    @NotEmpty(message = "员工编码employeeId不能为空")
    @ApiModelProperty(value = "员工编码", required = true)
    private String employeeId;

    @ApiModelProperty(value = "worktileId", required = false)
    private String worktileId;

    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @ApiModelProperty(value = "确认密码", required = true)
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
        return "AddAccountVO{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", qq=" + qq +
                ", weixin='" + weixin + '\'' +
                ", email='" + email + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", worktileId='" + worktileId + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
