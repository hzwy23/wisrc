package com.wisrc.merchandise.entity;


import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class MskuSaleNumEnity {
    private String msku;
    @ApiModelProperty(value = "昨日")
    private Integer lastNum;
    @ApiModelProperty(value = "前日")
    private Integer lastTwoNum;
    @ApiModelProperty(value = "上前日")
    private Integer lastThreeNum;
    private String sellerId;
    private String dataDt;
    private String shopId;
    private Date modifyTime;


    public Integer getLastNum() {
        return lastNum;
    }

    public void setLastNum(Integer lastNum) {
        this.lastNum = lastNum;
    }

    public Integer getLastTwoNum() {
        return lastTwoNum;
    }

    public void setLastTwoNum(Integer lastTwoNum) {
        this.lastTwoNum = lastTwoNum;
    }

    public Integer getLastThreeNum() {
        return lastThreeNum;
    }

    public void setLastThreeNum(Integer lastThreeNum) {
        this.lastThreeNum = lastThreeNum;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getDataDt() {
        return dataDt;
    }

    public void setDataDt(String dataDt) {
        this.dataDt = dataDt;
    }

    public String getMsku() {
        return msku;
    }

    public void setMsku(String msku) {
        this.msku = msku;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
