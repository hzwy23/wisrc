package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(tags = "装箱信息")
public class ProducPackingInfoEntity {
    @ApiModelProperty(value = "采购订单产品ID")
    private String id;
    @ApiModelProperty(value = "装箱率")
    private double packingRate;
    @ApiModelProperty(value = "长( cm)")
    private double packLong;
    @ApiModelProperty(value = "宽( cm)")
    private double packWide;
    @ApiModelProperty(value = " 高( cm)")
    private double packHigh;
    @ApiModelProperty(value = "体积(m3)")
    private double packVolume;
    @ApiModelProperty(value = "毛重(kg)")
    private double grossWeight;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPackVolume() {
        return packVolume;
    }

    public void setPackVolume(double packVolume) {
        this.packVolume = packVolume;
    }

    public double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(double grossWeight) {
        this.grossWeight = grossWeight;
    }

    @Override
    public String toString() {
        return "ProducPackingInfoEntity{" +
                "id='" + id + '\'' +
                ", packingRate=" + packingRate +
                ", packLong=" + packLong +
                ", packWide=" + packWide +
                ", packHigh=" + packHigh +
                ", packVolume=" + packVolume +
                ", grossWeight=" + grossWeight +
                '}';
    }
}
