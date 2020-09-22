package com.wisrc.merchandise.entity;

public class VMskuEntity {
    private String platId;
    private String platName;
    private String platSite;
    private String commodityId;
    private String mskuId;
    private String mskuName;
    private String skuId;
    private String employeeId;
    private String mskuStatusCd;
    private String shopId;
    private String shopName;
    private String shopStatusCd;

    public String getPlatId() {
        return platId;
    }

    public void setPlatId(String platId) {
        this.platId = platId;
    }

    public String getPlatName() {
        return platName;
    }

    public void setPlatName(String platName) {
        this.platName = platName;
    }

    public String getPlatSite() {
        return platSite;
    }

    public void setPlatSite(String platSite) {
        this.platSite = platSite;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
    }

    public String getMskuName() {
        return mskuName;
    }

    public void setMskuName(String mskuName) {
        this.mskuName = mskuName;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getMskuStatusCd() {
        return mskuStatusCd;
    }

    public void setMskuStatusCd(String mskuStatusCd) {
        this.mskuStatusCd = mskuStatusCd;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopStatusCd() {
        return shopStatusCd;
    }

    public void setShopStatusCd(String shopStatusCd) {
        this.shopStatusCd = shopStatusCd;
    }

    @Override
    public String toString() {
        return "VMskuEntity{" +
                "platId='" + platId + '\'' +
                ", platName='" + platName + '\'' +
                ", platSite='" + platSite + '\'' +
                ", commodityId='" + commodityId + '\'' +
                ", mskuId='" + mskuId + '\'' +
                ", mskuName='" + mskuName + '\'' +
                ", skuId='" + skuId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", mskuStatusCd='" + mskuStatusCd + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopStatusCd='" + shopStatusCd + '\'' +
                '}';
    }
}
