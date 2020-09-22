package com.wisrc.basic.entity;

public class BasicShopInfoEntity {

    private String shopId;

    private String platId;

    private String shopName;

    private String shopOwnerId;

    private String securityKey;

    private String awsAccessKey;

    private int statusCd;

    private String modifyUser;

    private String modifyTime;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getPlatId() {
        return platId;
    }

    public void setPlatId(String platId) {
        this.platId = platId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopOwnerId() {
        return shopOwnerId;
    }

    public void setShopOwnerId(String shopOwnerId) {
        this.shopOwnerId = shopOwnerId;
    }

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public String getAwsAccessKey() {
        return awsAccessKey;
    }

    public void setAwsAccessKey(String awsAccessKey) {
        this.awsAccessKey = awsAccessKey;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "BasicShopInfo{" +
                "shopId='" + shopId + '\'' +
                ", platId='" + platId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopOwnerId='" + shopOwnerId + '\'' +
                ", securityKey='" + securityKey + '\'' +
                ", awsAccessKey='" + awsAccessKey + '\'' +
                ", statusCd='" + statusCd + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                '}';
    }
}
