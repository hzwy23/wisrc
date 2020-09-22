package com.wisrc.replenishment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

public class ProductDetailsEnityVO {

    @NotEmpty(message = "店铺编码不能为空")
    @ApiModelProperty(value = "店铺编码", required = true)
    private String shopId;
    @ApiModelProperty(value = "MSKU编号", required = true)
    @NotEmpty(message = "MSKU编号不能为空")
    private String mskuId;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
    }
}
