package com.wisrc.sys.webapp.vo.privilegeShop;

import com.wisrc.sys.webapp.utils.Crypto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "商品信息")
public class CommodityVO implements Serializable {
    @ApiModelProperty(value = "商品ID", required = false, hidden = true)
    private String commodityId;

    @ApiModelProperty(value = "MSKU编码")
    private String mskuId;

    @ApiModelProperty(value = "店铺ID")
    private String shopId;

    public String getCommodityId() {
        return Crypto.sha(mskuId, shopId);
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

    @Override
    public String toString() {
        return "CommodityVO{" +
                "commodityId='" + commodityId + '\'' +
                ", mskuId='" + mskuId + '\'' +
                ", shopId='" + shopId + '\'' +
                '}';
    }
}
