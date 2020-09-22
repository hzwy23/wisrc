package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class TransferOrderDetailsEntity {
    @ApiModelProperty("明细唯一标识")
    private String commodityInfoCd;
    @ApiModelProperty("调拨单号")
    private String transferOrderId;
    @ApiModelProperty("产品编号")
    private String skuId;
    @ApiModelProperty("商品编号")
    private String mskuId;
    @ApiModelProperty("fnSku")
    private String fnSku;
    @ApiModelProperty("调拨数量")
    private Integer transferQuantity;
    @ApiModelProperty("发货数量")
    private Integer deliveryQuantity;
    @ApiModelProperty("签收数量")
    private Integer signInQuantity;
    @ApiModelProperty("装箱数量")
    private Integer packingQuantity;

    public String getCommodityInfoCd() {
        return commodityInfoCd;
    }

    public void setCommodityInfoCd(String commodityInfoCd) {
        this.commodityInfoCd = commodityInfoCd;
    }

    public String getTransferOrderId() {
        return transferOrderId;
    }

    public void setTransferOrderId(String transferOrderId) {
        this.transferOrderId = transferOrderId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
    }

    public String getFnSku() {
        return fnSku;
    }

    public void setFnSku(String fnSku) {
        this.fnSku = fnSku;
    }

    public Integer getTransferQuantity() {
        return transferQuantity;
    }

    public void setTransferQuantity(Integer transferQuantity) {
        this.transferQuantity = transferQuantity;
    }

    public Integer getDeliveryQuantity() {
        return deliveryQuantity;
    }

    public void setDeliveryQuantity(Integer deliveryQuantity) {
        this.deliveryQuantity = deliveryQuantity;
    }

    public Integer getSignInQuantity() {
        return signInQuantity;
    }

    public void setSignInQuantity(Integer signInQuantity) {
        this.signInQuantity = signInQuantity;
    }

    public Integer getPackingQuantity() {
        return packingQuantity;
    }

    public void setPackingQuantity(Integer packingQuantity) {
        this.packingQuantity = packingQuantity;
    }
}
