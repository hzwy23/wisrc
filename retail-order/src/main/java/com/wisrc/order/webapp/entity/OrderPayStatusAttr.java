package com.wisrc.order.webapp.entity;


public class OrderPayStatusAttr {

    private long payStatusCd;
    private String payStatusName;


    public long getPayStatusCd() {
        return payStatusCd;
    }

    public void setPayStatusCd(long payStatusCd) {
        this.payStatusCd = payStatusCd;
    }


    public String getPayStatusName() {
        return payStatusName;
    }

    public void setPayStatusName(String payStatusName) {
        this.payStatusName = payStatusName;
    }


    @Override
    public String toString() {
        return "OrderPayStatusAttr{" +
                "payStatusCd=" + payStatusCd +
                ", payStatusName='" + payStatusName + '\'' +
                '}';
    }
}
