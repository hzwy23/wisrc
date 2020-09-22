package com.wisrc.warehouse.webapp.vo;

public class DetailVO {
    private String orderId;
    private String entryTime;
    private int onwayNum;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public int getOnwayNum() {
        return onwayNum;
    }

    public void setOnwayNum(int onwayNum) {
        this.onwayNum = onwayNum;
    }
}
