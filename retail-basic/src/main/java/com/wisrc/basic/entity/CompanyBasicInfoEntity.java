package com.wisrc.basic.entity;

import lombok.Data;

@Data
public class CompanyBasicInfoEntity {
    private String companyOnlyId;
    private String companyName;
    private String companyNameEn;
    private String companyNameHk;
    private String companyAddress;
    private String companyAddressEn;
    private String companyAddressHk;
    private String companyTelephone;
    private String companyFax;
    private String taxpayerIdentificationNumber;

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

    @Override
    public String toString() {
        return "CompanyBasicInfoEntity{" +
                "companyOnlyId='" + companyOnlyId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyNameEn='" + companyNameEn + '\'' +
                ", companyNameHk='" + companyNameHk + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", companyAddressEn='" + companyAddressEn + '\'' +
                ", companyAddressHk='" + companyAddressHk + '\'' +
                ", companyTelephone='" + companyTelephone + '\'' +
                ", companyFax='" + companyFax + '\'' +
                '}';
    }
}
