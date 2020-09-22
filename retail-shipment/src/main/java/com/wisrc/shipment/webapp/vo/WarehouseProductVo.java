package com.wisrc.shipment.webapp.vo;

import java.util.List;

public class WarehouseProductVo {
    private String skuId;
    private String picture;
    private String skuNameZh;
    private List<WareHouseVo> changeLableDetailList;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSkuNameZh() {
        return skuNameZh;
    }

    public void setSkuNameZh(String skuNameZh) {
        this.skuNameZh = skuNameZh;
    }

    public List<WareHouseVo> getChangeLableDetailList() {
        return changeLableDetailList;
    }

    public void setChangeLableDetailList(List<WareHouseVo> changeLableDetailList) {
        this.changeLableDetailList = changeLableDetailList;
    }
}
