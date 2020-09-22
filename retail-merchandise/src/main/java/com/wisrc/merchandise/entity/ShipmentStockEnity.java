package com.wisrc.merchandise.entity;

public class ShipmentStockEnity {
    private String sellerId;
    private String msku;
    private String dataTime;
    private String reservedCustomerOrders;
    private String reservedFcTransfers;
    private String reservedFcProcessing;
    private String afnFulfillableQuantity;
    private String afnUnsellableQuantity;
    private String shopId;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getMsku() {
        return msku;
    }

    public void setMsku(String msku) {
        this.msku = msku;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getReservedCustomerOrders() {
        return reservedCustomerOrders;
    }

    public void setReservedCustomerOrders(String reservedCustomerOrders) {
        this.reservedCustomerOrders = reservedCustomerOrders;
    }

    public String getReservedFcTransfers() {
        return reservedFcTransfers;
    }

    public void setReservedFcTransfers(String reservedFcTransfers) {
        this.reservedFcTransfers = reservedFcTransfers;
    }

    public String getReservedFcProcessing() {
        return reservedFcProcessing;
    }

    public void setReservedFcProcessing(String reservedFcProcessing) {
        this.reservedFcProcessing = reservedFcProcessing;
    }

    public String getAfnFulfillableQuantity() {
        return afnFulfillableQuantity;
    }

    public void setAfnFulfillableQuantity(String afnFulfillableQuantity) {
        this.afnFulfillableQuantity = afnFulfillableQuantity;
    }

    public String getAfnUnsellableQuantity() {
        return afnUnsellableQuantity;
    }

    public void setAfnUnsellableQuantity(String afnUnsellableQuantity) {
        this.afnUnsellableQuantity = afnUnsellableQuantity;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
