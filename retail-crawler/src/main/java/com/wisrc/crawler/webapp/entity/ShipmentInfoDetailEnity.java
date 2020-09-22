package com.wisrc.crawler.webapp.entity;

public class ShipmentInfoDetailEnity {
    private String shipmentId;
    private String sellerId;
    private String msku;
    private String fnsku;
    private String quantityShipped;
    private String quantityInCase;
    private String quantityReceived;

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

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

    public String getFnsku() {
        return fnsku;
    }

    public void setFnsku(String fnsku) {
        this.fnsku = fnsku;
    }

    public String getQuantityShipped() {
        return quantityShipped;
    }

    public void setQuantityShipped(String quantityShipped) {
        this.quantityShipped = quantityShipped;
    }

    public String getQuantityInCase() {
        return quantityInCase;
    }

    public void setQuantityInCase(String quantityInCase) {
        this.quantityInCase = quantityInCase;
    }

    public String getQuantityReceived() {
        return quantityReceived;
    }

    public void setQuantityReceived(String quantityReceived) {
        this.quantityReceived = quantityReceived;
    }
}
