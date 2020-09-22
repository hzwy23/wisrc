package com.wisrc.order.webapp.entity;


public class OrderNvoiceInfo {

    private String invoiceNumber;
    private int statusCd;
    private String wmsWaveNumber;
    private String createTime;
    private String createUser;
    private Double totalWeight;
    private String shipmentId;
    private String logisticsId;
    private Double freight;


    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getWmsWaveNumber() {
        return wmsWaveNumber;
    }

    public void setWmsWaveNumber(String wmsWaveNumber) {
        this.wmsWaveNumber = wmsWaveNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    @Override
    public String toString() {
        return "OrderNvoiceInfo{" +
                "invoiceNumber='" + invoiceNumber + '\'' +
                ", statusCd=" + statusCd +
                ", wmsWaveNumber='" + wmsWaveNumber + '\'' +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", totalWeight=" + totalWeight +
                ", shipmentId='" + shipmentId + '\'' +
                ", logisticsId='" + logisticsId + '\'' +
                ", freight=" + freight +
                '}';
    }
}
