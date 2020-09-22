package com.wisrc.order.webapp.entity;


public class OrderLabelInfo {

    private String uuid;
    private String orderId;
    private String exceptTypeCd;


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


    public String getExceptTypeCd() {
        return exceptTypeCd;
    }

    public void setExceptTypeCd(String exceptTypeCd) {
        this.exceptTypeCd = exceptTypeCd;
    }


    @Override
    public String toString() {
        return "OrderLabelInfo{" +
                "uuid='" + uuid + '\'' +
                ", orderId='" + orderId + '\'' +
                ", exceptTypeCd='" + exceptTypeCd + '\'' +
                '}';
    }
}
