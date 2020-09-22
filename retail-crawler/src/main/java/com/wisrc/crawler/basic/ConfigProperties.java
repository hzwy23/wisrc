package com.wisrc.crawler.basic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class ConfigProperties {
    @Value("${shipmentInfo.trackingUrl}")
    private String trackingUrl;
    @Value("${shipmentInfo.saleRecordUrl}")
    private String saleRecordUrl;
    @Value("${shipmentInfo.mskuStockUrl}")
    private String mskuStockUrl;
    @Value("${shipmentInfo.shipmentInfoUrl}")
    private String shipmentInfoUrl;
    @Value("${shipmentInfo.removeOrderInfoUrl}")
    private String removeOrderInfoUrl;
    @Value("${shipmentInfo.mskuInfoUrl}")
    private String mskuInfoUrl;
    @Value("${shipmentInfo.shelveInfoUrl}")
    private String shelveInfoUrl;
    @Value("${shipmentInfo.saleRecordBatchUrl}")
    private String saleRecordBatchUrl;


    public String getTrackingUrl() {
        return trackingUrl;
    }

    public void setTrackingUrl(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }

    public String getSaleRecordUrl() {
        return saleRecordUrl;
    }

    public void setSaleRecordUrl(String saleRecordUrl) {
        this.saleRecordUrl = saleRecordUrl;
    }

    public String getMskuStockUrl() {
        return mskuStockUrl;
    }

    public void setMskuStockUrl(String mskuStockUrl) {
        this.mskuStockUrl = mskuStockUrl;
    }

    public String getShipmentInfoUrl() {
        return shipmentInfoUrl;
    }

    public void setShipmentInfoUrl(String shipmentInfoUrl) {
        this.shipmentInfoUrl = shipmentInfoUrl;
    }

    public String getRemoveOrderInfoUrl() {
        return removeOrderInfoUrl;
    }

    public void setRemoveOrderInfoUrl(String removeOrderInfoUrl) {
        this.removeOrderInfoUrl = removeOrderInfoUrl;
    }

    public String getMskuInfoUrl() {
        return mskuInfoUrl;
    }

    public void setMskuInfoUrl(String mskuInfoUrl) {
        this.mskuInfoUrl = mskuInfoUrl;
    }

    public String getShelveInfoUrl() {
        return shelveInfoUrl;
    }

    public void setShelveInfoUrl(String shelveInfoUrl) {
        this.shelveInfoUrl = shelveInfoUrl;
    }

    public String getSaleRecordBatchUrl() {
        return saleRecordBatchUrl;
    }

    public void setSaleRecordBatchUrl(String saleRecordBatchUrl) {
        this.saleRecordBatchUrl = saleRecordBatchUrl;
    }
}
