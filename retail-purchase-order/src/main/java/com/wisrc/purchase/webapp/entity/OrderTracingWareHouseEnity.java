package com.wisrc.purchase.webapp.entity;


import java.util.Date;

public class OrderTracingWareHouseEnity {
    private String orderId;
    private String skuId;
    private String entryId;
    private int entryNum;
    private int entryFrets;
    private Date entryTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public int getEntryNum() {
        return entryNum;
    }

    public void setEntryNum(int entryNum) {
        this.entryNum = entryNum;
    }

    public int getEntryFrets() {
        return entryFrets;
    }

    public void setEntryFrets(int entryFrets) {
        this.entryFrets = entryFrets;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }
}
