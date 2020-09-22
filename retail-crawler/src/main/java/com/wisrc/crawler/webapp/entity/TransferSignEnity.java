package com.wisrc.crawler.webapp.entity;

public class TransferSignEnity {
    private String msku;
    private String sellerId;
    private String returnApplyId;
    private String shopId;
    private Integer signNum;

    public String getMsku() {
        return msku;
    }

    public void setMsku(String msku) {
        this.msku = msku;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getReturnApplyId() {
        return returnApplyId;
    }

    public void setReturnApplyId(String returnApplyId) {
        this.returnApplyId = returnApplyId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Integer getSignNum() {
        return signNum;
    }

    public void setSignNum(Integer signNum) {
        this.signNum = signNum;
    }
}
