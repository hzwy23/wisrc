package com.wisrc.order.webapp.entity;

public class OrderRedeliveryInfo {

    private String redeliveryId;
    private String orderId;
    private long redeliveryTypeCd;
    private String offerId;
    private String logisticsId;
    private long redeliveryNumber;
    private String createUser;
    private java.sql.Timestamp createTime;
    private String modifyUser;
    private java.sql.Timestamp modifyTime;


    public String getRedeliveryId() {
        return redeliveryId;
    }

    public void setRedeliveryId(String redeliveryId) {
        this.redeliveryId = redeliveryId;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public long getRedeliveryTypeCd() {
        return redeliveryTypeCd;
    }

    public void setRedeliveryTypeCd(long redeliveryTypeCd) {
        this.redeliveryTypeCd = redeliveryTypeCd;
    }


    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }


    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }


    public long getRedeliveryNumber() {
        return redeliveryNumber;
    }

    public void setRedeliveryNumber(long redeliveryNumber) {
        this.redeliveryNumber = redeliveryNumber;
    }


    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }


    public java.sql.Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }


    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }


    public java.sql.Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(java.sql.Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }


    @Override
    public String toString() {
        return "OrderRedeliveryInfo{" +
                "redeliveryId='" + redeliveryId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", redeliveryTypeCd=" + redeliveryTypeCd +
                ", offerId='" + offerId + '\'' +
                ", logisticsId='" + logisticsId + '\'' +
                ", redeliveryNumber=" + redeliveryNumber +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
