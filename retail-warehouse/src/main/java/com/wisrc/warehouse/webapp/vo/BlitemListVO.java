package com.wisrc.warehouse.webapp.vo;

import com.wisrc.warehouse.webapp.entity.BlitemListInfoEntity;

import java.util.List;

public class BlitemListVO extends BlitemListInfoEntity {
    private String skuName;
    private List<String> imgUrls;

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }
}
