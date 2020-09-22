package com.wisrc.order.webapp.entity;


public class OrderPayTypeAttr {

    private long payTypeCd;
    private String payTypeDesc;


    public long getPayTypeCd() {
        return payTypeCd;
    }

    public void setPayTypeCd(long payTypeCd) {
        this.payTypeCd = payTypeCd;
    }


    public String getPayTypeDesc() {
        return payTypeDesc;
    }

    public void setPayTypeDesc(String payTypeDesc) {
        this.payTypeDesc = payTypeDesc;
    }


    @Override
    public String toString() {
        return "OrderPayTypeAttr{" +
                "payTypeCd=" + payTypeCd +
                ", payTypeDesc='" + payTypeDesc + '\'' +
                '}';
    }
}
