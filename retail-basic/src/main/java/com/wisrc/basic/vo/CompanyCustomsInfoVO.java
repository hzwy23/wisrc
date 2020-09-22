package com.wisrc.basic.vo;

import com.wisrc.basic.entity.CompanyCustomsInfoEntity;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotEmpty;

public class CompanyCustomsInfoVO {
    @ApiModelProperty(value = "报关编号", required = true)
    @NotEmpty
    private String soleId;
    @ApiModelProperty(value = "公司编号", required = true)
    @NotEmpty
    private String companyOnlyId;
    @ApiModelProperty(value = "贸易模式(1--一般贸易（默认）)", required = true)
    @NotEmpty
    private int tradeModeCd;
    @ApiModelProperty(value = "出口口岸(海运)(默认1--深圳)", required = true)
    @NotEmpty
    private int deliveryPortCdHy;
    @ApiModelProperty(value = "出口口岸(空运)(默认0--全选)", required = true)
    @NotEmpty
    private int deliveryPortCdKy;
    @ApiModelProperty(value = "征免性质(1--一般征税（默认）)", required = true)
    @NotEmpty
    private int exemptingNatureCd;
    @ApiModelProperty(value = "征免模式(1--照章征税（默认）)", required = true)
    @NotEmpty
    private int exemptionModeCd;
    @ApiModelProperty(value = "交易模式(1--FOB(默认))", required = true)
    @NotEmpty
    private int transactionModeCd;
    @ApiModelProperty(value = "货币制度(1--USD（默认）)", required = true)
    @NotEmpty
    private int monetarySystemCd;
    @ApiModelProperty(value = "包装类型(1--纸箱（默认）)", required = true)
    @NotEmpty
    private int packingTypeCd;
    @ApiModelProperty(value = "海关编码", required = true)
    @NotEmpty
    private String customsCode;
    @ApiModelProperty(value = "增值税税号", required = true)
    @NotEmpty
    private String vatTaxNumber;
    @ApiModelProperty(value = "联系人中文名", required = true)
    @NotEmpty
    private String contacts;
    @ApiModelProperty(value = "联系人英文名", required = true)
    @NotEmpty
    private String contactsEn;
    @ApiModelProperty(value = "手机号码", required = true)
    @NotEmpty
    private String cellphone;
    @ApiModelProperty(value = "贸易国(空运)", required = true)
    @NotEmpty
    private String tradingCountryEmpty;
    @ApiModelProperty(value = "贸易国(海运)", required = true)
    @NotEmpty
    private String tradingCountrySea;
    @ApiModelProperty(value = "合同签约地", required = true)
    @NotEmpty
    private String contractingPlace;
    @ApiModelProperty(value = "境内货源地", required = true)
    @NotEmpty
    private String sourcingDestinations;
    @ApiModelProperty(value = "仓库地址", required = true)
    @NotEmpty
    private String warehouseAddress;
    @ApiModelProperty(value = "VAT税号", required = true)
    @NotEmpty
    private String vatCd;
    @ApiModelProperty(value = "标记唛头", required = true)
    @NotEmpty
    private String signMark;
    @ApiModelProperty(value = "运抵国", required = true)
    private String arrivalCountry;

    public static final CompanyCustomsInfoVO toVO(CompanyCustomsInfoEntity ele) {
        CompanyCustomsInfoVO vo = new CompanyCustomsInfoVO();
        BeanUtils.copyProperties(ele, vo);
        return vo;
    }

    public String getArrivalCountry() {
        return arrivalCountry;
    }

    public void setArrivalCountry(String arrivalCountry) {
        this.arrivalCountry = arrivalCountry;
    }

    public String getSoleId() {
        return soleId;
    }

    public void setSoleId(String soleId) {
        this.soleId = soleId;
    }

    public String getCompanyOnlyId() {
        return companyOnlyId;
    }

    public void setCompanyOnlyId(String companyOnlyId) {
        this.companyOnlyId = companyOnlyId;
    }

    public int getTradeModeCd() {
        return tradeModeCd;
    }

    public void setTradeModeCd(int tradeModeCd) {
        this.tradeModeCd = tradeModeCd;
    }

    public int getDeliveryPortCdHy() {
        return deliveryPortCdHy;
    }

    public void setDeliveryPortCdHy(int deliveryPortCdHy) {
        this.deliveryPortCdHy = deliveryPortCdHy;
    }

    public int getDeliveryPortCdKy() {
        return deliveryPortCdKy;
    }

    public void setDeliveryPortCdKy(int deliveryPortCdKy) {
        this.deliveryPortCdKy = deliveryPortCdKy;
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

    public int getTransactionModeCd() {
        return transactionModeCd;
    }

    public void setTransactionModeCd(int transactionModeCd) {
        this.transactionModeCd = transactionModeCd;
    }

    public int getMonetarySystemCd() {
        return monetarySystemCd;
    }

    public void setMonetarySystemCd(int monetarySystemCd) {
        this.monetarySystemCd = monetarySystemCd;
    }

    public int getPackingTypeCd() {
        return packingTypeCd;
    }

    public void setPackingTypeCd(int packingTypeCd) {
        this.packingTypeCd = packingTypeCd;
    }

    public String getCustomsCode() {
        return customsCode;
    }

    public void setCustomsCode(String customsCode) {
        this.customsCode = customsCode;
    }

    public String getVatTaxNumber() {
        return vatTaxNumber;
    }

    public void setVatTaxNumber(String vatTaxNumber) {
        this.vatTaxNumber = vatTaxNumber;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactsEn() {
        return contactsEn;
    }

    public void setContactsEn(String contactsEn) {
        this.contactsEn = contactsEn;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getTradingCountryEmpty() {
        return tradingCountryEmpty;
    }

    public void setTradingCountryEmpty(String tradingCountryEmpty) {
        this.tradingCountryEmpty = tradingCountryEmpty;
    }

    public String getTradingCountrySea() {
        return tradingCountrySea;
    }

    public void setTradingCountrySea(String tradingCountrySea) {
        this.tradingCountrySea = tradingCountrySea;
    }

    public String getContractingPlace() {
        return contractingPlace;
    }

    public void setContractingPlace(String contractingPlace) {
        this.contractingPlace = contractingPlace;
    }

    public String getSourcingDestinations() {
        return sourcingDestinations;
    }

    public void setSourcingDestinations(String sourcingDestinations) {
        this.sourcingDestinations = sourcingDestinations;
    }

    public String getWarehouseAddress() {
        return warehouseAddress;
    }

    public void setWarehouseAddress(String warehouseAddress) {
        this.warehouseAddress = warehouseAddress;
    }

    public String getVatCd() {
        return vatCd;
    }

    public void setVatCd(String vatCd) {
        this.vatCd = vatCd;
    }

    public String getSignMark() {
        return signMark;
    }

    public void setSignMark(String signMark) {
        this.signMark = signMark;
    }

    @Override
    public String toString() {
        return "CompanyCustomsInfoVO{" +
                "soleId='" + soleId + '\'' +
                ", companyOnlyId='" + companyOnlyId + '\'' +
                ", tradeModeCd=" + tradeModeCd +
                ", deliveryPortCdHy=" + deliveryPortCdHy +
                ", deliveryPortCdKy=" + deliveryPortCdKy +
                ", exemptingNatureCd=" + exemptingNatureCd +
                ", exemptionModeCd=" + exemptionModeCd +
                ", transactionModeCd=" + transactionModeCd +
                ", monetarySystemCd=" + monetarySystemCd +
                ", packingTypeCd=" + packingTypeCd +
                ", customsCode='" + customsCode + '\'' +
                ", vatTaxNumber='" + vatTaxNumber + '\'' +
                ", contacts='" + contacts + '\'' +
                ", contactsEn='" + contactsEn + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", tradingCountryEmpty='" + tradingCountryEmpty + '\'' +
                ", tradingCountrySea='" + tradingCountrySea + '\'' +
                ", contractingPlace='" + contractingPlace + '\'' +
                ", sourcingDestinations='" + sourcingDestinations + '\'' +
                ", warehouseAddress='" + warehouseAddress + '\'' +
                ", vatCd='" + vatCd + '\'' +
                ", signMark='" + signMark + '\'' +
                ", arrivalCountry='" + arrivalCountry + '\'' +
                '}';
    }
}
