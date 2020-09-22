package com.wisrc.sys.webapp.entity;

public class SysPrivilegesInfoEntity {

    private String privilegeCd;
    private String privilegeName;
    private Integer statusCd;
    private String createUser;
    private String createTime;
    private String updateTime;
    private Integer privilegeTypeAttr;
    private String modifyUser;
    private String modifyTime;

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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPrivilegeTypeAttr() {
        return privilegeTypeAttr;
    }

    public void setPrivilegeTypeAttr(Integer privilegesTypeAttr) {
        this.privilegeTypeAttr = privilegesTypeAttr;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "SysPrivilegesInfoEntity{" +
                "privilegeCd='" + privilegeCd + '\'' +
                ", privilegeName='" + privilegeName + '\'' +
                ", statusCd=" + statusCd +
                ", createUser='" + createUser + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", privilegesTypeAttr=" + privilegeTypeAttr +
                '}';
    }
}
