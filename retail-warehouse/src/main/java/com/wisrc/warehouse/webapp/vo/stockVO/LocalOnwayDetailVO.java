package com.wisrc.warehouse.webapp.vo.stockVO;

public class LocalOnwayDetailVO {
    private String orderId;
    private String orderType;
    private String warehouseName;
    private String estimateTime;
    private int onwayNum;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(String estimateTime) {
        this.estimateTime = estimateTime;
    }

    public int getOnwayNum() {
        return onwayNum;
    }

    public void setOnwayNum(int onwayNum) {
        this.onwayNum = onwayNum;
    }
}
