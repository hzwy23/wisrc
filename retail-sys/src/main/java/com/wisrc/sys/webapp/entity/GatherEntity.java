package com.wisrc.sys.webapp.entity;

import lombok.Data;

import java.util.Date;

@Data
public class GatherEntity {
    private String employeeId;
    private String employeeName;
    private String positionCd;
    private String positionName;
    private String deptCd;
    private String deptName;

    private String uuid;
    private String privilegeCd;
    private String shopId;
    private String commodityId;
    private String createUser;

    private Date createTime;


    //SysPrivilegesInfoEntity
    private String privilegeName;
    private Integer statusCd;
    private Date updateTime;

    //SysPrivilegeSupplierEntity
    private String supplierCd;

    private String userId;

    private String userName;
    private String phoneNumber;
    private Integer qq;
    private String weixin;
    private String email;
    private String telephoneNumber;
    private String worktileId;


    private String warehouseCd;


    @Override
    public String toString() {
        return "GatherEntity{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", positionCd='" + positionCd + '\'' +
                ", positionName='" + positionName + '\'' +
                ", deptCd='" + deptCd + '\'' +
                ", deptName='" + deptName + '\'' +
                ", uuid='" + uuid + '\'' +
                ", privilegeCd='" + privilegeCd + '\'' +
                ", shopId='" + shopId + '\'' +
                ", commodityId='" + commodityId + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", privilegeName='" + privilegeName + '\'' +
                ", statusCd=" + statusCd +
                ", updateTime=" + updateTime +
                ", supplierCd='" + supplierCd + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", qq=" + qq +
                ", weixin='" + weixin + '\'' +
                ", email='" + email + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", worktileId='" + worktileId + '\'' +
                ", warehouseCd='" + warehouseCd + '\'' +
                '}';
    }
}
