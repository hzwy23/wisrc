package com.wisrc.sys.webapp.bo;


public class MskuDTO {
    private String id;
    private String commodityId;
    private String mskuId;
    private String shopId;
    private String shopName;
    private String mskuName;
    private Integer mskuStatusCd;
    private String skuId;
    private String productName;
    private String platformCd;
    private String platformName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.commodityId = id;
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

    public String getMskuName() {
        return mskuName;
    }

    public void setMskuName(String mskuName) {
        this.mskuName = mskuName;
    }

    public Integer getMskuStatusCd() {
        return mskuStatusCd;
    }

    public void setMskuStatusCd(Integer mskuStatusCd) {
        this.mskuStatusCd = mskuStatusCd;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformCd() {
        return platformCd;
    }

    public void setPlatformCd(String platformCd) {
        this.platformCd = platformCd;
    }

    @Override
    public String toString() {
        return "MskuDTO{" +
                "id='" + id + '\'' +
                ", commodityId='" + commodityId + '\'' +
                ", mskuId='" + mskuId + '\'' +
                ", shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", mskuName='" + mskuName + '\'' +
                ", mskuStatusCd=" + mskuStatusCd +
                ", skuId='" + skuId + '\'' +
                ", productName='" + productName + '\'' +
                ", platformName='" + platformName + '\'' +
                '}';
    }
}
