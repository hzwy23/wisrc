package com.wisrc.replenishment.webapp.vo.wms;

public class LogisticsDeclareDocSyncVO {
    private String voucherCode;
    private String customsInfoUrl;
    private String logisticsInfoUrl;
    private String shipmentName;
    private String boxDetailUrl;
    private String clearanceInvoiceUrl;

    public String getBoxDetailUrl() {
        return boxDetailUrl;
    }

    public void setBoxDetailUrl(String boxDetailUrl) {
        this.boxDetailUrl = boxDetailUrl;
    }

    public String getClearanceInvoiceUrl() {
        return clearanceInvoiceUrl;
    }

    public void setClearanceInvoiceUrl(String clearanceInvoiceUrl) {
        this.clearanceInvoiceUrl = clearanceInvoiceUrl;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getCustomsInfoUrl() {
        return customsInfoUrl;
    }

    public void setCustomsInfoUrl(String customsInfoUrl) {
        this.customsInfoUrl = customsInfoUrl;
    }

    public String getLogisticsInfoUrl() {
        return logisticsInfoUrl;
    }

    public void setLogisticsInfoUrl(String logisticsInfoUrl) {
        this.logisticsInfoUrl = logisticsInfoUrl;
    }

    public String getShipmentName() {
        return shipmentName;
    }

    public void setShipmentName(String shipmentName) {
        this.shipmentName = shipmentName;
    }
}
