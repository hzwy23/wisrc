package com.wisrc.merchandise.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;

@Data
public class MskuPageEntity {
    private String id;
    private String mskuId;
    private String shopId;
    private String shopName;
    private String mskuName;
    private String skuId;
    private String parentAsin;
    private String userId;
    private Integer salesStatusCd;
    private Timestamp updateTime;
    private Integer mskuStatusCd;
    private String epitaph;
    private String asin;
    private String fnSkuId;
    private Timestamp shelfDate;
    private Integer deliveryMode;
    private String deliveryModeDesc;
    private Double commission;

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Timestamp getShelfTime() {
        return shelfDate;
    }

    public void setShelfTime(Timestamp shelfDate) {
        this.shelfDate = shelfDate;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getFnsku() {
        return fnSkuId;
    }

    public void setFnsku(String fnSkuId) {
        this.fnSkuId = fnSkuId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getParentAsin() {
        return parentAsin;
    }

    public void setParentAsin(String parentAsin) {
        this.parentAsin = parentAsin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getSalesStatusCd() {
        return salesStatusCd;
    }

    public void setSalesStatusCd(Integer salesStatusCd) {
        this.salesStatusCd = salesStatusCd;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getMskuStatusCd() {
        return mskuStatusCd;
    }

    public void setMskuStatusCd(Integer mskuStatusCd) {
        this.mskuStatusCd = mskuStatusCd;
    }

    public String getEpitaph() {
        return epitaph;
    }

    public void setEpitaph(String epitaph) {
        this.epitaph = epitaph;
    }

    public Integer getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(Integer deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getFnSkuId() {
        return fnSkuId;
    }

    public void setFnSkuId(String fnSkuId) {
        this.fnSkuId = fnSkuId;
    }

    public Timestamp getShelfDate() {
        return shelfDate;
    }

    public void setShelfDate(Timestamp shelfDate) {
        this.shelfDate = shelfDate;
    }

    public String getDeliveryModeDesc() {
        return deliveryModeDesc;
    }

    public void setDeliveryModeDesc(String deliveryModeDesc) {
        this.deliveryModeDesc = deliveryModeDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MskuPageEntity that = (MskuPageEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(mskuId, that.mskuId) &&
                Objects.equals(shopId, that.shopId) &&
                Objects.equals(mskuName, that.mskuName) &&
                Objects.equals(skuId, that.skuId) &&
                Objects.equals(parentAsin, that.parentAsin) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(epitaph, that.epitaph);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, mskuId, shopId, mskuName, skuId, parentAsin, userId, updateTime, epitaph);
    }

    @Override
    public String toString() {
        return "MskuPageEntity{" +
                "id='" + id + '\'' +
                ", mskuId='" + mskuId + '\'' +
                ", shopId='" + shopId + '\'' +
                ", mskuName='" + mskuName + '\'' +
                ", skuId='" + skuId + '\'' +
                ", parentAsin='" + parentAsin + '\'' +
                ", userId='" + userId + '\'' +
                ", salesStatusCd=" + salesStatusCd +
                ", updateTime=" + updateTime +
                ", mskuStatusCd=" + mskuStatusCd +
                ", epitaph='" + epitaph + '\'' +
                ", asin='" + asin + '\'' +
                ", fnSkuId='" + fnSkuId + '\'' +
                ", shelfDate=" + shelfDate +
                ", deliveryMode=" + deliveryMode +
                '}';
    }
}
