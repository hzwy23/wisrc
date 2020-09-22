package com.wisrc.shipment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class DestinationEnityVo {
    @ApiModelProperty(value = "报价ID", hidden = true)
    private String offerId;
    @ApiModelProperty(value = "目的地编号")
    private String destinationCd;
    @ApiModelProperty(value = "物流商id", hidden = true)
    private String shipmentId;
    @ApiModelProperty(value = "区名", hidden = true)
    private String destinationName;
    @ApiModelProperty(value = "国家名", hidden = true)
    private String countryName;

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getDestinationCd() {
        return destinationCd;
    }

    public void setDestinationCd(String destinationCd) {
        this.destinationCd = destinationCd;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
