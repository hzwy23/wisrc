package com.wisrc.order.webapp.entity;

public class OrderDeliveryChannelAttrEntity {

    private int deliveryChannelCd;
    private String deliveryChannelName;


    public int getDeliveryChannelCd() {
        return deliveryChannelCd;
    }

    public void setDeliveryChannelCd(int deliveryChannelCd) {
        this.deliveryChannelCd = deliveryChannelCd;
    }

    public String getDeliveryChannelName() {
        return deliveryChannelName;
    }

    public void setDeliveryChannelName(String deliveryChannelName) {
        this.deliveryChannelName = deliveryChannelName;
    }


    @Override
    public String toString() {
        return "OrderDeliveryChannelAttr{" +
                "deliveryChannelCd=" + deliveryChannelCd +
                ", deliveryChannelName='" + deliveryChannelName + '\'' +
                '}';
    }
}
