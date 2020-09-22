package com.wisrc.shipment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class LogisticsChargeTypeAttrEntity {
    @ApiModelProperty(value = "计费类型ID", required = true)
    private int chargeTypeCd;
    @ApiModelProperty(value = "渠道类型ID", required = true)
    private int channelTypeCd;
    @ApiModelProperty(value = "计费类型描述", required = true)
    private String chargeTypeDesc;

    public int getChargeTypeCd() {
        return chargeTypeCd;
    }

    public void setChargeTypeCd(int chargeTypeCd) {
        this.chargeTypeCd = chargeTypeCd;
    }

    public int getChannelTypeCd() {
        return channelTypeCd;
    }

    public void setChannelTypeCd(int channelTypeCd) {
        this.channelTypeCd = channelTypeCd;
    }

    public String getChargeTypeDesc() {
        return chargeTypeDesc;
    }

    public void setChargeTypeDesc(String chargeTypeDesc) {
        this.chargeTypeDesc = chargeTypeDesc;
    }

    @Override
    public String toString() {
        return "LogisticsChargeTypeAttrEntity{" +
                "chargeTypeCd=" + chargeTypeCd +
                ", channelTypeCd=" + channelTypeCd +
                ", chargeTypeDesc='" + chargeTypeDesc + '\'' +
                '}';
    }
}
