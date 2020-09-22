package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class VReplenishmentMskuEntity {
    @ApiModelProperty(value = "商品唯一ID号")
    private String commodityId;

    @ApiModelProperty(value = "MSKU ID值")
    private String mskuId;

    @ApiModelProperty(value = "店铺ID")
    private String shopId;

    @ApiModelProperty(value = "FBA发货数量")
    private String deliveryNumberTotal;

    @ApiModelProperty(value = "FBA收货数量")
    private String signInQuantityTotal;

    @ApiModelProperty(value = "FBA在途数量")
    private String underWayQuantity;

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

    public String getDeliveryNumberTotal() {
        return deliveryNumberTotal;
    }

    public void setDeliveryNumberTotal(String deliveryNumberTotal) {
        this.deliveryNumberTotal = deliveryNumberTotal;
    }

    public String getSignInQuantityTotal() {
        return signInQuantityTotal;
    }

    public void setSignInQuantityTotal(String signInQuantityTotal) {
        this.signInQuantityTotal = signInQuantityTotal;
    }

    public String getUnderWayQuantity() {
        return underWayQuantity;
    }

    public void setUnderWayQuantity(String underWayQuantity) {
        this.underWayQuantity = underWayQuantity;
    }

    @Override
    public String toString() {
        return "VReplenishmentMskuEntity{" +
                "commodityId='" + commodityId + '\'' +
                ", mskuId='" + mskuId + '\'' +
                ", shopId='" + shopId + '\'' +
                ", deliveryNumberTotal='" + deliveryNumberTotal + '\'' +
                ", signInQuantityTotal='" + signInQuantityTotal + '\'' +
                ", underWayQuantity='" + underWayQuantity + '\'' +
                '}';
    }
}
