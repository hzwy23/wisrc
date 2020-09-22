package com.wisrc.replenishment.webapp.entity;


import io.swagger.annotations.ApiModelProperty;

public class LogisticOfferEnity {
    @ApiModelProperty(value = "物流单号")
    private String logisticsId;
    @ApiModelProperty(value = "报价id")
    private String offerId;
    @ApiModelProperty(value = "货运公司代号")
    private String logisticsTrackUrl;

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getLogisticsTrackUrl() {
        return logisticsTrackUrl;
    }

    public void setLogisticsTrackUrl(String logisticsTrackUrl) {
        this.logisticsTrackUrl = logisticsTrackUrl;
    }
}
