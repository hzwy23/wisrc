package com.wisrc.basic.vo;

import io.swagger.annotations.ApiModelProperty;

public class ShopInfoVO {
    private String storeName;

    @ApiModelProperty(hidden = true)
    private String platform;

    @ApiModelProperty(hidden = true)
    private String siteName;
    @ApiModelProperty(hidden = true)
    private int statusCd;
    private String sellerNo;
    private String key;
    private String aws;

    @ApiModelProperty(hidden = true)
    private String updateTime;
    private String platId;

    @ApiModelProperty(hidden = true)
    private String shopId;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getSellerNo() {
        return sellerNo;
    }

    public void setSellerNo(String sellerNo) {
        this.sellerNo = sellerNo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAws() {
        return aws;
    }

    public void setAws(String aws) {
        this.aws = aws;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getPlatId() {
        return platId;
    }

    public void setPlatId(String platId) {
        this.platId = platId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "ShopInfoVO{" +
                "storeName='" + storeName + '\'' +
                ", platform='" + platform + '\'' +
                ", siteName='" + siteName + '\'' +
                ", statusCd=" + statusCd +
                ", sellerNo='" + sellerNo + '\'' +
                ", key='" + key + '\'' +
                ", aws='" + aws + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
