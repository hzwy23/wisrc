package com.wisrc.shipment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductDetail {
    @NotEmpty
    private String mskuId;
    @NotEmpty
    private String commodityId;
    @Min(value = 0, message = "售价必须为正数")
    private Double salePrice;
    @NotNull(message = "申请数量不能为空")
    private Integer applyReturnNum;
    @ApiModelProperty(value = "退仓申请Id", hidden = true)
    private String returnApplyId;
    @ApiModelProperty(value = "唯一标识", hidden = true)
    private String uuid;
    @ApiModelProperty(value = "库存sku", hidden = true)
    private String skuId;
    @ApiModelProperty(value = "fnsku编码", hidden = true)
    private String fnsku;

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getApplyReturnNum() {
        return applyReturnNum;
    }

    public void setApplyReturnNum(Integer applyReturnNum) {
        this.applyReturnNum = applyReturnNum;
    }

    public String getReturnApplyId() {
        return returnApplyId;
    }

    public void setReturnApplyId(String returnApplyId) {
        this.returnApplyId = returnApplyId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getFnsku() {
        return fnsku;
    }

    public void setFnsku(String fnsku) {
        this.fnsku = fnsku;
    }
}
