package com.wisrc.order.webapp.vo;

import java.util.List;

public class ProductAndWareHouseVo {
    private String mskuName;
    private String picture;
    private String skuId;
    private String skuNameZh;
    private String commodityId;
    private Double unitWeight;
    private List<WareHouseVo> wareHouseList;

    public String getMskuName() {
        return mskuName;
    }

    public void setMskuName(String mskuName) {
        this.mskuName = mskuName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuNameZh() {
        return skuNameZh;
    }

    public void setSkuNameZh(String skuNameZh) {
        this.skuNameZh = skuNameZh;
    }


    public List<WareHouseVo> getWareHouseList() {
        return wareHouseList;
    }

    public void setWareHouseList(List<WareHouseVo> wareHouseList) {
        this.wareHouseList = wareHouseList;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public Double getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(Double unitWeight) {
        this.unitWeight = unitWeight;
    }
}
