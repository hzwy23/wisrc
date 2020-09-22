package com.wisrc.warehouse.webapp.entity;

public class EnterWarehouseTypeEntity {
    private int enterTypeCd;
    private String typeName;
    private String createUser;
    private String createTime;
    private int deleteStatus;

    public int getEnterTypeCd() {
        return enterTypeCd;
    }

    public void setEnterTypeCd(int enterTypeCd) {
        this.enterTypeCd = enterTypeCd;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
