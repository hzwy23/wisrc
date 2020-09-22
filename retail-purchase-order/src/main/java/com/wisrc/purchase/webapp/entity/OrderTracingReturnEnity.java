package com.wisrc.purchase.webapp.entity;


import java.util.Date;

public class OrderTracingReturnEnity {
    private String orderId;
    private String skuId;
    private String returnBill;
    private int returnQuantity;
    private int spareQuantity;
    private Date createDate;

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

    public String getReturnBill() {
        return returnBill;
    }

    public void setReturnBill(String returnBill) {
        this.returnBill = returnBill;
    }

    public int getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(int returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public int getSpareQuantity() {
        return spareQuantity;
    }

    public void setSpareQuantity(int spareQuantity) {
        this.spareQuantity = spareQuantity;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
