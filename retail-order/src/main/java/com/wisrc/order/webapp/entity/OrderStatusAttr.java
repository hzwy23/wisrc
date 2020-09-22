package com.wisrc.order.webapp.entity;

public class OrderStatusAttr {

    private Integer statusCd;
    private String statusName;


    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
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
        return "OrderStatusAttr{" +
                "statusCd=" + statusCd +
                ", statusName='" + statusName + '\'' +
                '}';
    }
}
