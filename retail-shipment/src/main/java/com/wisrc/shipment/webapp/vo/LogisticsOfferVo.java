package com.wisrc.shipment.webapp.vo;

import com.wisrc.shipment.webapp.entity.LogisticsOfferDestinationEnity;

import java.util.List;

public class LogisticsOfferVo {
    private String offerId;
    private String channelName;
    private List<LogisticsOfferDestinationEnity> logisticsOfferDestinationEnityList;

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public List<LogisticsOfferDestinationEnity> getLogisticsOfferDestinationEnityList() {
        return logisticsOfferDestinationEnityList;
    }

    public void setLogisticsOfferDestinationEnityList(List<LogisticsOfferDestinationEnity> logisticsOfferDestinationEnityList) {
        this.logisticsOfferDestinationEnityList = logisticsOfferDestinationEnityList;
    }
}
