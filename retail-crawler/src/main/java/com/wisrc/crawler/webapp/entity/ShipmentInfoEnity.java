package com.wisrc.crawler.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ShipmentInfoEnity {
    @ApiModelProperty(value = "货件标题")
    private String shipmentName;
    @ApiModelProperty(value = "货件运输状态")
    private String shipmentStatus;
    @ApiModelProperty(value = "货件承运人")
    private String carrierName;
    @ApiModelProperty(value = "是否合作人")
    private String isPartnered;
    @ApiModelProperty(value = "货件类型")
    private String shipmentType;
    @ApiModelProperty(value = "预估费用")
    private Double amountValue;
    @ApiModelProperty(value = "物流跟踪单号")
    private List<String> tracingIds;
    @ApiModelProperty(value = "FBA收货仓编号")
    private String destinationFulfillmentCenterId;
    private List shipmentInfoItemList;
    private String sellerId;
    private String shipmentId;

    public String getShipmentName() {
        return shipmentName;
    }

    public void setShipmentName(String shipmentName) {
        this.shipmentName = shipmentName;
    }

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }


    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
    }


    public String getDestinationFulfillmentCenterId() {
        return destinationFulfillmentCenterId;
    }

    public void setDestinationFulfillmentCenterId(String destinationFulfillmentCenterId) {
        this.destinationFulfillmentCenterId = destinationFulfillmentCenterId;
    }

    public List getShipmentInfoItemList() {
        return shipmentInfoItemList;
    }

    public void setShipmentInfoItemList(List shipmentInfoItemList) {
        this.shipmentInfoItemList = shipmentInfoItemList;
    }

    public Double getAmountValue() {
        return amountValue;
    }

    public void setAmountValue(Double amountValue) {
        this.amountValue = amountValue;
    }

    public String getIsPartnered() {
        return isPartnered;
    }

    public void setIsPartnered(String isPartnered) {
        this.isPartnered = isPartnered;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public List<String> getTracingIds() {
        return tracingIds;
    }

    public void setTracingIds(List<String> tracingIds) {
        this.tracingIds = tracingIds;
    }
}
