package com.wisrc.order.webapp.entity;


public class OrderRedeliveryProductInfo {

    private String uuid;
    private String redeliveryId;
    private String commodityId;
    private long redeliveryQuantity;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public String getRedeliveryId() {
        return redeliveryId;
    }

    public void setRedeliveryId(String redeliveryId) {
        this.redeliveryId = redeliveryId;
    }


    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }


    public long getRedeliveryQuantity() {
        return redeliveryQuantity;
    }

    public void setRedeliveryQuantity(long redeliveryQuantity) {
        this.redeliveryQuantity = redeliveryQuantity;
    }


    @Override
    public String toString() {
        return "OrderRedeliveryProductInfo{" +
                "uuid='" + uuid + '\'' +
                ", redeliveryId='" + redeliveryId + '\'' +
                ", commodityId='" + commodityId + '\'' +
                ", redeliveryQuantity=" + redeliveryQuantity +
                '}';
    }
}
