package com.wisrc.merchandise.entity;


public class BasicPlatformInfoEntity {

    private String platId;

    private String platName;

    private String platSite;

    private String apiUrl;

    private int statusCd;

    private String modifyUser;

    private String modifyTime;

    private String marketPlaceId;

    private String fbaWarehouseId;

    public String getFbaWarehouseId() {
        return fbaWarehouseId;
    }

    public void setFbaWarehouseId(String fbaWarehouseId) {
        this.fbaWarehouseId = fbaWarehouseId;
    }

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

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
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

    public String getMarketPlaceId() {
        return marketPlaceId;
    }

    public void setMarketPlaceId(String marketPlaceId) {
        this.marketPlaceId = marketPlaceId;
    }

    @Override
    public String toString() {
        return "BasicPlatformInfoEntity{" +
                "platId='" + platId + '\'' +
                ", platName='" + platName + '\'' +
                ", platSite='" + platSite + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                ", statusCd=" + statusCd +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", marketPlaceId='" + marketPlaceId + '\'' +
                ", fbaWarehouseId='" + fbaWarehouseId + '\'' +
                '}';
    }
}
