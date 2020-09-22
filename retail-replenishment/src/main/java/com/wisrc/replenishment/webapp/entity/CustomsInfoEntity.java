package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class CustomsInfoEntity {
    @ApiModelProperty(value = "运单号")
    private String waybillId;
    @ApiModelProperty(value = "报关编号")
    private String customsDeclareId;
    @ApiModelProperty(value = "报关日期")
    private String customsDeclarationDate;
    @ApiModelProperty(value = "贸易国")
    private String tradeCountry;
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    @ApiModelProperty(value = "公司地址")
    private String companyAddress;
    @ApiModelProperty(value = "运抵国")
    private String destinationCountry;
    @ApiModelProperty(value = "运输方式")
    private int transportTypeCd;
    @ApiModelProperty(value = "运输终点")
    private String destinationAddress;
    @ApiModelProperty(value = "贸易方式")
    private int tradeTypeCd;
    @ApiModelProperty(value = "出口口岸")
    private int outPortCd;
    @ApiModelProperty(value = "征免性质")
    private int exemptingNatureCd;
    @ApiModelProperty(value = "征免方式")
    private int exemptionModeCd;
    @ApiModelProperty(value = "境内货源地")
    private String goodsSource;
    @ApiModelProperty(value = "合同签约地点")
    private String signAddress;
    @ApiModelProperty(value = "成交方式")
    private int dealTypeCd;
    @ApiModelProperty(value = "外汇币制")
    private int moneyTypeCd;
    @ApiModelProperty(value = "包装种类")
    private int packTypeCd;
    @ApiModelProperty(value = "标记唛头")
    private String signMark;
    @ApiModelProperty(value = "报关单号")
    private String customsDeclareNumber;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "报关资料电子档")
    private String customsInfoDoc;
    @ApiModelProperty(value = "报关单电子档")
    private String declarationNumberDoc;
    @ApiModelProperty(value = "放行通知书")
    private String letterReleaseDoc;


    public String getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(String waybillId) {
        this.waybillId = waybillId;
    }

    public String getCustomsDeclareId() {
        return customsDeclareId;
    }

    public void setCustomsDeclareId(String customsDeclareId) {
        this.customsDeclareId = customsDeclareId;
    }

    public String getCustomsDeclarationDate() {
        return customsDeclarationDate;
    }

    public void setCustomsDeclarationDate(String customsDeclarationDate) {
        this.customsDeclarationDate = customsDeclarationDate;
    }

    public String getTradeCountry() {
        return tradeCountry;
    }

    public void setTradeCountry(String tradeCountry) {
        this.tradeCountry = tradeCountry;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public int getTransportTypeCd() {
        return transportTypeCd;
    }

    public void setTransportTypeCd(int transportTypeCd) {
        this.transportTypeCd = transportTypeCd;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public int getTradeTypeCd() {
        return tradeTypeCd;
    }

    public void setTradeTypeCd(int tradeTypeCd) {
        this.tradeTypeCd = tradeTypeCd;
    }

    public int getOutPortCd() {
        return outPortCd;
    }

    public void setOutPortCd(int outPortCd) {
        this.outPortCd = outPortCd;
    }

    public int getExemptingNatureCd() {
        return exemptingNatureCd;
    }

    public void setExemptingNatureCd(int exemptingNatureCd) {
        this.exemptingNatureCd = exemptingNatureCd;
    }

    public int getExemptionModeCd() {
        return exemptionModeCd;
    }

    public void setExemptionModeCd(int exemptionModeCd) {
        this.exemptionModeCd = exemptionModeCd;
    }

    public String getGoodsSource() {
        return goodsSource;
    }

    public void setGoodsSource(String goodsSource) {
        this.goodsSource = goodsSource;
    }

    public String getSignAddress() {
        return signAddress;
    }

    public void setSignAddress(String signAddress) {
        this.signAddress = signAddress;
    }

    public int getDealTypeCd() {
        return dealTypeCd;
    }

    public void setDealTypeCd(int dealTypeCd) {
        this.dealTypeCd = dealTypeCd;
    }

    public int getMoneyTypeCd() {
        return moneyTypeCd;
    }

    public void setMoneyTypeCd(int moneyTypeCd) {
        this.moneyTypeCd = moneyTypeCd;
    }

    public int getPackTypeCd() {
        return packTypeCd;
    }

    public void setPackTypeCd(int packTypeCd) {
        this.packTypeCd = packTypeCd;
    }

    public String getSignMark() {
        return signMark;
    }

    public void setSignMark(String signMark) {
        this.signMark = signMark;
    }

    public String getCustomsDeclareNumber() {
        return customsDeclareNumber;
    }

    public void setCustomsDeclareNumber(String customsDeclareNumber) {
        this.customsDeclareNumber = customsDeclareNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCustomsInfoDoc() {
        return customsInfoDoc;
    }

    public void setCustomsInfoDoc(String customsInfoDoc) {
        this.customsInfoDoc = customsInfoDoc;
    }

    public String getDeclarationNumberDoc() {
        return declarationNumberDoc;
    }

    public void setDeclarationNumberDoc(String declarationNumberDoc) {
        this.declarationNumberDoc = declarationNumberDoc;
    }

    public String getLetterReleaseDoc() {
        return letterReleaseDoc;
    }

    public void setLetterReleaseDoc(String letterReleaseDoc) {
        this.letterReleaseDoc = letterReleaseDoc;
    }
}
