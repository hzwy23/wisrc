package com.wisrc.purchase.webapp.entity;


public class OrderTracingRejectionEnity {
    private String rejectionId;
    private String orderId;
    private String skuId;
    private String rejectionDate;
    private Integer spareQuantity;
    private Integer rejectQuantity;

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

    public Integer getSpareQuantity() {
        return spareQuantity;
    }

    public void setSpareQuantity(Integer spareQuantity) {
        this.spareQuantity = spareQuantity;
    }

    public Integer getRejectQuantity() {
        return rejectQuantity;
    }

    public void setRejectQuantity(Integer rejectQuantity) {
        this.rejectQuantity = rejectQuantity;
    }

    public String getRejectionId() {
        return rejectionId;
    }

    public void setRejectionId(String rejectionId) {
        this.rejectionId = rejectionId;
    }

    public String getRejectionDate() {
        return rejectionDate;
    }

    public void setRejectionDate(String rejectionDate) {
        this.rejectionDate = rejectionDate;
    }
}
