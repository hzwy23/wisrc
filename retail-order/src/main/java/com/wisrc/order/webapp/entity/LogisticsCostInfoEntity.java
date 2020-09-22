package com.wisrc.order.webapp.entity;


public class LogisticsCostInfoEntity {

    private String uuid;
    private String batchNumber;
    private String offerId;
    private String shipmentId;
    private String shipmentName;
    private String channelName;
    private String logisticsId;
    private double freight;
    private String createUser;
    private String createTime;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }


    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }


    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }


    public String getShipmentName() {
        return shipmentName;
    }

    public void setShipmentName(String shipmentName) {
        this.shipmentName = shipmentName;
    }


    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }


    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }


    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }


    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "LogisticsCostInfo{" +
                "uuid='" + uuid + '\'' +
                ", batchNumber='" + batchNumber + '\'' +
                ", offerId='" + offerId + '\'' +
                ", shipmentId='" + shipmentId + '\'' +
                ", shipmentName='" + shipmentName + '\'' +
                ", channelName='" + channelName + '\'' +
                ", logisticsId='" + logisticsId + '\'' +
                ", freight=" + freight +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
