package com.wisrc.basic.entity;

public class CompanyCustomsInfoEntity {
    private String soleId;
    private String companyOnlyId;
    private int tradeModeCd;
    private int deliveryPortCdHy;
    private int deliveryPortCdKy;
    private int exemptingNatureCd;
    private int exemptionModeCd;
    private int transactionModeCd;
    private int monetarySystemCd;
    private int packingTypeCd;
    private String customsCode;
    private String vatTaxNumber;
    private String contacts;
    private String contactsEn;
    private String cellphone;
    private String tradingCountryEmpty;
    private String tradingCountrySea;
    private String contractingPlace;
    private String sourcingDestinations;
    private String warehouseAddress;
    private String vatCd;
    private String signMark;
    private String arrivalCountry;

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
        return "CompanyCustomsInfoEntity{" +
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
