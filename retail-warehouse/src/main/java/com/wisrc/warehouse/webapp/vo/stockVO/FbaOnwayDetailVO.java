package com.wisrc.warehouse.webapp.vo.stockVO;

public class FbaOnwayDetailVO {
    private String batchNumber;
    private String startWarehouse;
    private String endWarehouse;
    private int deliveryNumber;
    private String deliveryTime;
    private String estimateDate;
    private String shipmentChannel;
    private String logisticsId;

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getStartWarehouse() {
        return startWarehouse;
    }

    public void setStartWarehouse(String startWarehouse) {
        this.startWarehouse = startWarehouse;
    }

    public String getEndWarehouse() {
        return endWarehouse;
    }

    public void setEndWarehouse(String endWarehouse) {
        this.endWarehouse = endWarehouse;
    }

    public int getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(int deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(String estimateDate) {
        this.estimateDate = estimateDate;
    }

    public String getShipmentChannel() {
        return shipmentChannel;
    }

    public void setShipmentChannel(String shipmentChannel) {
        this.shipmentChannel = shipmentChannel;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }
}
