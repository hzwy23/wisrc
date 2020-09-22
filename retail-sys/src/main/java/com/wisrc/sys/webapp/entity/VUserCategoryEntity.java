package com.wisrc.sys.webapp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "员工与类目主管信息表")
public class VUserCategoryEntity {

    @ApiModelProperty(value = "账号ID")
    private String userId;
    @ApiModelProperty(value = "账号昵称")
    private String userName;
    @ApiModelProperty(value = "员工ID")
    private String employeeId;
    @ApiModelProperty(value = "员工名称")
    private String employeeName;
    @ApiModelProperty(value = "岗位ID")
    private String positionCd;
    @ApiModelProperty(value = "岗位名称")
    private String positionName;
    @ApiModelProperty(value = "是否类目主管。0：不是，1：是")
    private Integer executiveDirectorAttr;
    @ApiModelProperty(value = "上级岗位ID")
    private String upPositionCd;
    @ApiModelProperty(value = "上级岗位名称")
    private String upPositionName;
    @ApiModelProperty(value = "上级岗位是否类目主管")
    private Integer upExecutiveDirectorAttr;
    @ApiModelProperty(value = "上级岗位员工ID")
    private String upEmployeeId;
    @ApiModelProperty(value = "上级岗位员工名称")
    private String upEmployeeName;
    @ApiModelProperty(value = "类目主管ID")
    private String executiveDirectorId;
    @ApiModelProperty(value = "类目主管名称")
    private String executiveDirectorName;

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

    public Integer getExecutiveDirectorAttr() {
        return executiveDirectorAttr;
    }

    public void setExecutiveDirectorAttr(Integer executiveDirectorAttr) {
        this.executiveDirectorAttr = executiveDirectorAttr;
    }

    public String getUpPositionCd() {
        return upPositionCd;
    }

    public void setUpPositionCd(String upPositionCd) {
        this.upPositionCd = upPositionCd;
    }

    public String getUpPositionName() {
        return upPositionName;
    }

    public void setUpPositionName(String upPositionName) {
        this.upPositionName = upPositionName;
    }

    public Integer getUpExecutiveDirectorAttr() {
        return upExecutiveDirectorAttr;
    }

    public void setUpExecutiveDirectorAttr(Integer upExecutiveDirectorAttr) {
        this.upExecutiveDirectorAttr = upExecutiveDirectorAttr;
    }

    public String getUpEmployeeId() {
        return upEmployeeId;
    }

    public void setUpEmployeeId(String upEmployeeId) {
        this.upEmployeeId = upEmployeeId;
    }

    public String getUpEmployeeName() {
        return upEmployeeName;
    }

    public void setUpEmployeeName(String upEmployeeName) {
        this.upEmployeeName = upEmployeeName;
    }

    public String getExecutiveDirectorId() {
        return executiveDirectorId;
    }

    public void setExecutiveDirectorId(String executiveDirectorId) {
        this.executiveDirectorId = executiveDirectorId;
    }

    public String getExecutiveDirectorName() {
        return executiveDirectorName;
    }

    public void setExecutiveDirectorName(String executiveDirectorName) {
        this.executiveDirectorName = executiveDirectorName;
    }

    @Override
    public String toString() {
        return "VUserCategoryEntity{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", positionCd='" + positionCd + '\'' +
                ", positionName='" + positionName + '\'' +
                ", executiveDirectorAttr=" + executiveDirectorAttr +
                ", upPositionCd='" + upPositionCd + '\'' +
                ", upPositionName='" + upPositionName + '\'' +
                ", upExecutiveDirectorAttr=" + upExecutiveDirectorAttr +
                ", upEmployeeId='" + upEmployeeId + '\'' +
                ", upEmployeeName='" + upEmployeeName + '\'' +
                ", executiveDirectorId='" + executiveDirectorId + '\'' +
                ", executiveDirectorName='" + executiveDirectorName + '\'' +
                '}';
    }
}
