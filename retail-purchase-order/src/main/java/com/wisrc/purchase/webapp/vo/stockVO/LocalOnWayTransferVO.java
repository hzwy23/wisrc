package com.wisrc.purchase.webapp.vo.stockVO;

public class LocalOnWayTransferVO {
    private String arrivalId;
    private String planDeliveryTime;
    private int planDeliveryQuantity;

    public String getArrivalId() {
        return arrivalId;
    }

    public void setArrivalId(String arrivalId) {
        this.arrivalId = arrivalId;
    }

    public String getPlanDeliveryTime() {
        return planDeliveryTime;
    }

    public void setPlanDeliveryTime(String planDeliveryTime) {
        this.planDeliveryTime = planDeliveryTime;
    }

    public int getPlanDeliveryQuantity() {
        return planDeliveryQuantity;
    }

    public void setPlanDeliveryQuantity(int planDeliveryQuantity) {
        this.planDeliveryQuantity = planDeliveryQuantity;
    }
}
