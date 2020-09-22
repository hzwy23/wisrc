package com.wisrc.basic.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CompanyBasicInfoAllVO {
    @ApiModelProperty(value = "公司编号")
    private String companyOnlyId;
    @ApiModelProperty(value = "公司名称")
    @NotEmpty(message = "公司名称不能为空")
    private String companyName;
    @ApiModelProperty(value = "公司英文名")
    @NotEmpty(message = "公司英文名不能为空")
    private String companyNameEn;
    @ApiModelProperty(value = "公司香港名")
    @NotEmpty(message = "公司香港名不能为空")
    private String companyNameHk;
    @ApiModelProperty(value = "公司地址")
    @NotEmpty(message = "公司地址不能为空")
    private String companyAddress;
    @ApiModelProperty(value = "公司英文地址")
    @NotEmpty(message = "公司英文地址不能为空")
    private String companyAddressEn;
    @ApiModelProperty(value = "公司香港地址")
    @NotEmpty(message = "公司香港地址不能为空")
    private String companyAddressHk;
    @ApiModelProperty(value = "公司固话")
    @NotEmpty(message = "公司固话不能为空")
    private String companyTelephone;
    @ApiModelProperty(value = "公司传真")
    private String companyFax;
    @ApiModelProperty(value = "纳税人识别号")
    @NotEmpty(message = "纳税人识别号不能为空")
    private String taxpayerIdentificationNumber;
    @ApiModelProperty(value = "报关编号")
    private String soleId;
    @ApiModelProperty(value = "贸易模式(1--一般贸易（默认）)")
    private int tradeModeCd;
    @ApiModelProperty(value = "出口口岸(海运)(默认1--深圳)")
    private int deliveryPortCdHy;
    @ApiModelProperty(value = "出口口岸(空运)(默认0--全选)")
    private int deliveryPortCdKy;
    @ApiModelProperty(value = "征免性质(1--一般征税（默认）)")
    private int exemptingNatureCd;
    @ApiModelProperty(value = "征免方式(1--照章征税（默认）)")
    private int exemptionModeCd;
    @ApiModelProperty(value = "成交方式(1--FOB(默认))")
    private int transactionModeCd;
    @ApiModelProperty(value = "货币制度(1--USD（默认）)")
    private int monetarySystemCd;
    @ApiModelProperty(value = "包装类型(1--纸箱（默认）)")
    private int packingTypeCd;
    @ApiModelProperty(value = "海关编码")
    @NotEmpty(message = "海关编码不能为空")
    private String customsCode;
    @ApiModelProperty(value = "增值税税号")
    private String vatTaxNumber;
    @ApiModelProperty(value = "联系人中文名")
    @NotEmpty(message = "联系人中文名不能为空")
    private String contacts;
    @ApiModelProperty(value = "联系人英文名")
    private String contactsEn;
    @ApiModelProperty(value = "手机号码")
    @NotEmpty(message = "手机号码不能为空")
    private String cellphone;
    @ApiModelProperty(value = "贸易国(空运)")
    @NotEmpty(message = "贸易国(空运)不能为空")
    private String tradingCountryEmpty;
    @ApiModelProperty(value = "贸易国(海运)")
    @NotEmpty(message = "贸易国(海运)不能为空")
    private String tradingCountrySea;
    @ApiModelProperty(value = "合同签约地")
    @NotEmpty(message = "合同签约地不能为空")
    private String contractingPlace;
    @ApiModelProperty(value = "境内货源地")
    @NotEmpty(message = "境内货源地不能为空")
    private String sourcingDestinations;
    @ApiModelProperty(value = "仓库地址")
    private String warehouseAddress;
    @ApiModelProperty(value = "VAT税号")
    @NotEmpty(message = "VAT税号不能为空")
    private String vatCd;
    @ApiModelProperty(value = "标记唛头")
    @NotEmpty(message = "标记唛头不能为空")
    private String signMark;
    @ApiModelProperty(value = "运抵国")
    @NotEmpty(message = "运抵国不能为空")
    private String arrivalCountry;

    public String getArrivalCountry() {
        return arrivalCountry;
    }

    public void setArrivalCountry(String arrivalCountry) {
        this.arrivalCountry = arrivalCountry;
    }

    public String getCompanyOnlyId() {
        return companyOnlyId;
    }

    public void setCompanyOnlyId(String companyOnlyId) {
        this.companyOnlyId = companyOnlyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNameEn() {
        return companyNameEn;
    }

    public void setCompanyNameEn(String companyNameEn) {
        this.companyNameEn = companyNameEn;
    }

    public String getCompanyNameHk() {
        return companyNameHk;
    }

    public void setCompanyNameHk(String companyNameHk) {
        this.companyNameHk = companyNameHk;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyAddressEn() {
        return companyAddressEn;
    }

    public void setCompanyAddressEn(String companyAddressEn) {
        this.companyAddressEn = companyAddressEn;
    }

    public String getCompanyAddressHk() {
        return companyAddressHk;
    }

    public void setCompanyAddressHk(String companyAddressHk) {
        this.companyAddressHk = companyAddressHk;
    }

    public String getCompanyTelephone() {
        return companyTelephone;
    }

    public void setCompanyTelephone(String companyTelephone) {
        this.companyTelephone = companyTelephone;
    }

    public String getCompanyFax() {
        return companyFax;
    }

    public void setCompanyFax(String companyFax) {
        this.companyFax = companyFax;
    }

    public String getSoleId() {
        return soleId;
    }

    public void setSoleId(String soleId) {
        this.soleId = soleId;
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
        return "CompanyBasicInfoAllVO{" +
                "companyOnlyId='" + companyOnlyId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyNameEn='" + companyNameEn + '\'' +
                ", companyNameHk='" + companyNameHk + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", companyAddressEn='" + companyAddressEn + '\'' +
                ", companyAddressHk='" + companyAddressHk + '\'' +
                ", companyTelephone='" + companyTelephone + '\'' +
                ", companyFax='" + companyFax + '\'' +
                ", soleId='" + soleId + '\'' +
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
                '}';
    }
}
