package com.wisrc.replenishment.webapp.vo.delivery;

import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.List;

/**
 * 物流渠道报价信息
 */
public class LogisticsInfoVO {

    @ApiModelProperty(value = "物流商id")
    private String offerId;
    @ApiModelProperty(value = "物流商名称")
    private String shipmentName;
    @ApiModelProperty(value = "渠道名称")
    private String channelName;
    @ApiModelProperty(value = "目的地集合")
    private List<HashMap> destinationList;
    @ApiModelProperty(value = "类型")
    private String channelTypeCd;
    @ApiModelProperty(value = "计费区间")
    private String chargeIntever;
    @ApiModelProperty(value = "计费类型")
    private String chargeTypeCd;
    @ApiModelProperty(value = "含油价")
    private String unitPriceWithOil;
    @ApiModelProperty(value = "时效集合")
    private String logisticsOfferEffective;
    @ApiModelProperty(value = "过关费")
    private Double customsDeclarationFee;
    @ApiModelProperty(value = "过港费")
    private Double portFee;


    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getShipmentName() {
        return shipmentName;
    }

    public void setShipmentName(String shipmentName) {
        this.shipmentName = shipmentName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public List<HashMap> getDestinationList() {
        return destinationList;
    }

    public void setDestinationList(List<HashMap> destinationList) {
        this.destinationList = destinationList;
    }

    public String getChannelTypeCd() {
        return channelTypeCd;
    }

    public void setChannelTypeCd(String channelTypeCd) {
        this.channelTypeCd = channelTypeCd;
    }

    public String getChargeIntever() {
        return chargeIntever;
    }

    public void setChargeIntever(String chargeIntever) {
        this.chargeIntever = chargeIntever;
    }

    public String getChargeTypeCd() {
        return chargeTypeCd;
    }

    public void setChargeTypeCd(String chargeTypeCd) {
        this.chargeTypeCd = chargeTypeCd;
    }

    public String getUnitPriceWithOil() {
        return unitPriceWithOil;
    }

    public void setUnitPriceWithOil(String unitPriceWithOil) {
        this.unitPriceWithOil = unitPriceWithOil;
    }

    public String getLogisticsOfferEffective() {
        return logisticsOfferEffective;
    }

    public void setLogisticsOfferEffective(String logisticsOfferEffective) {
        this.logisticsOfferEffective = logisticsOfferEffective;
    }

    public Double getCustomsDeclarationFee() {
        return customsDeclarationFee;
    }

    public void setCustomsDeclarationFee(Double customsDeclarationFee) {
        this.customsDeclarationFee = customsDeclarationFee;
    }

    public Double getPortFee() {
        return portFee;
    }

    public void setPortFee(Double portFee) {
        this.portFee = portFee;
    }
}
