package com.wisrc.shipment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class LogisticsChannelAttrEntity {
    @ApiModelProperty(value = "渠道类型ID，1--空运，2--海运", required = true)
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

    @Override
    public String toString() {
        return "LogisticsChannelAttrEntity{" +
                "channelTypeCd=" + channelTypeCd +
                ", channelTypeDesc='" + channelTypeDesc + '\'' +
                '}';
    }
}
