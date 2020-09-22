package com.wisrc.order.webapp.entity;


public class OrderModifyHistory {

    private String uuid;
    private String orderId;
    private java.sql.Timestamp handleTime;
    private String handleUser;
    private String modifyColumn;
    private String oldValue;
    private String newValue;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public java.sql.Timestamp getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(java.sql.Timestamp handleTime) {
        this.handleTime = handleTime;
    }


    public String getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(String handleUser) {
        this.handleUser = handleUser;
    }


    public String getModifyColumn() {
        return modifyColumn;
    }

    public void setModifyColumn(String modifyColumn) {
        this.modifyColumn = modifyColumn;
    }


    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }


    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }


    @Override
    public String toString() {
        return "OrderModifyHistory{" +
                "uuid='" + uuid + '\'' +
                ", orderId='" + orderId + '\'' +
                ", handleTime=" + handleTime +
                ", handleUser='" + handleUser + '\'' +
                ", modifyColumn='" + modifyColumn + '\'' +
                ", oldValue='" + oldValue + '\'' +
                ", newValue='" + newValue + '\'' +
                '}';
    }
}
