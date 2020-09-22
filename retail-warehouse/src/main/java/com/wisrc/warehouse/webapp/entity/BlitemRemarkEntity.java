package com.wisrc.warehouse.webapp.entity;

public class BlitemRemarkEntity {
    private String uuid;
    private String blitemId;
    private String remarkContent;
    private String remarkTime;
    private String remarkUser;
    private String remarkUserName;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getBlitemId() {
        return blitemId;
    }

    public void setBlitemId(String blitemId) {
        this.blitemId = blitemId;
    }

    public String getRemarkContent() {
        return remarkContent;
    }

    public void setRemarkContent(String remarkContent) {
        this.remarkContent = remarkContent;
    }

    public String getRemarkTime() {
        return remarkTime;
    }

    public void setRemarkTime(String remarkTime) {
        this.remarkTime = remarkTime;
    }

    public String getRemarkUser() {
        return remarkUser;
    }

    public void setRemarkUser(String remarkUser) {
        this.remarkUser = remarkUser;
    }

    public String getRemarkUserName() {
        return remarkUserName;
    }

    public void setRemarkUserName(String remarkUserName) {
        this.remarkUserName = remarkUserName;
    }
}
