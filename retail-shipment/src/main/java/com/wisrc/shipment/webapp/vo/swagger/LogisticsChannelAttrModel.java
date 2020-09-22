package com.wisrc.shipment.webapp.vo.swagger;

import io.swagger.annotations.ApiModelProperty;

public class LogisticsChannelAttrModel {
    @ApiModelProperty(value = "渠道类型ID", required = true)
    private int channelTypeCd;
    @ApiModelProperty(value = "渠道描述", required = true)
    private String channelTypeDesc;

    public int getChannelTypeCd() {
        return channelTypeCd;
    }

    public void setChannelTypeCd(int channelTypeCd) {
        this.channelTypeCd = channelTypeCd;
    }

    public String getChannelTypeDesc() {
        return channelTypeDesc;
    }

    public void setChannelTypeDesc(String channelTypeDesc) {
        this.channelTypeDesc = channelTypeDesc;
    }
}
