package com.wisrc.order.webapp.entity;


public class OrderRedeliveryTypeAttr {

    private long redeliveryTypeCd;
    private String redeliveryTypeName;


    public long getRedeliveryTypeCd() {
        return redeliveryTypeCd;
    }

    public void setRedeliveryTypeCd(long redeliveryTypeCd) {
        this.redeliveryTypeCd = redeliveryTypeCd;
    }


    public String getRedeliveryTypeName() {
        return redeliveryTypeName;
    }

    public void setRedeliveryTypeName(String redeliveryTypeName) {
        this.redeliveryTypeName = redeliveryTypeName;
    }


    @Override
    public String toString() {
        return "OrderRedeliveryTypeAttr{" +
                "redeliveryTypeCd=" + redeliveryTypeCd +
                ", redeliveryTypeName='" + redeliveryTypeName + '\'' +
                '}';
    }
}
