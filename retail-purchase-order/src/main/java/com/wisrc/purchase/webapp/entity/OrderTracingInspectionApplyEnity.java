package com.wisrc.purchase.webapp.entity;

public class OrderTracingInspectionApplyEnity {
    private String arrivalId;
    private String skuId;
    private String orderId;
    private int inspectionQuantity;
    private int unqualifiedQuantity;
    private int qualifiedQuantity;
    private int finishQuantity;

    public String getArrivalId() {
        return arrivalId;
    }

    public void setArrivalId(String arrivalId) {
        this.arrivalId = arrivalId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getInspectionQuantity() {
        return inspectionQuantity;
    }

    public void setInspectionQuantity(int inspectionQuantity) {
        this.inspectionQuantity = inspectionQuantity;
    }

    public int getUnqualifiedQuantity() {
        return unqualifiedQuantity;
    }

    public void setUnqualifiedQuantity(int unqualifiedQuantity) {
        this.unqualifiedQuantity = unqualifiedQuantity;
    }

    public int getQualifiedQuantity() {
        return qualifiedQuantity;
    }

    public void setQualifiedQuantity(int qualifiedQuantity) {
        this.qualifiedQuantity = qualifiedQuantity;
    }

    public int getFinishQuantity() {
        return finishQuantity;
    }

    public void setFinishQuantity(int finishQuantity) {
        this.finishQuantity = finishQuantity;
    }
}
