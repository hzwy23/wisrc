package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(tags = "物流跟踪单相关产品信息")
public class WaybillMskuInfoEntity {
    @ApiModelProperty(value = "商品编码")
    private String mskuId;
    @ApiModelProperty(value = "补货数量")
    private double replenishmentQuantity;

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
    }

    public double getReplenishmentQuantity() {
        return replenishmentQuantity;
    }

    public void setReplenishmentQuantity(double replenishmentQuantity) {
        this.replenishmentQuantity = replenishmentQuantity;
    }

    @Override
    public String toString() {
        return "WaybillMskuInfoEntity{" +
                "mskuId='" + mskuId + '\'' +
                ", replenishmentQuantity=" + replenishmentQuantity +
                '}';
    }
}
