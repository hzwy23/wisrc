package com.wisrc.purchase.webapp.vo;

import com.wisrc.purchase.webapp.entity.ProducPackingInfoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

@ApiModel
public class ProducPackingInfoVO {
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

    public static final ProducPackingInfoVO toVO(ProducPackingInfoEntity ele) {
        ProducPackingInfoVO vo = new ProducPackingInfoVO();
        BeanUtils.copyProperties(ele, vo);
        return vo;
    }

    public double getPackingRate() {
        return packingRate;
    }

    public void setPackingRate(double packingRate) {
        this.packingRate = packingRate;
    }

    public double getPackLong() {
        return packLong;
    }

    public void setPackLong(double packLong) {
        this.packLong = packLong;
    }

    public double getPackWide() {
        return packWide;
    }

    public void setPackWide(double packWide) {
        this.packWide = packWide;
    }

    public double getPackHigh() {
        return packHigh;
    }

    public void setPackHigh(double packHigh) {
        this.packHigh = packHigh;
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
        return "ProducPackingInfoVO{" +
                "packingRate=" + packingRate +
                ", packLong=" + packLong +
                ", packWide=" + packWide +
                ", packHigh=" + packHigh +
                ", packVolume=" + packVolume +
                ", grossWeight=" + grossWeight +
                '}';
    }
}
