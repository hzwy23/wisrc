package com.wisrc.order.webapp.entity;


public class OrderCommodityStatusAttrEntity {

    private int statusCd;
    private String statusName;

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }


    @Override
    public String toString() {
        return "OrderCommodityStatusAttr{" +
                "statusCd=" + statusCd +
                ", statusName='" + statusName + '\'' +
                '}';
    }
}
