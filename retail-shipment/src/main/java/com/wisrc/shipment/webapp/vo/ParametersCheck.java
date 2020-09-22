package com.wisrc.shipment.webapp.vo;

import com.wisrc.shipment.webapp.entity.LogisticsOfferHistoryInfoEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ParametersCheck {
    @ApiModelProperty(value = "渠道名称")
    private String channelName;

    private List<LogisticsOfferHistoryInfoEntity> hisList;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public List<LogisticsOfferHistoryInfoEntity> getHisList() {
        return hisList;
    }

    public void setHisList(List<LogisticsOfferHistoryInfoEntity> hisList) {
        this.hisList = hisList;
    }
}
