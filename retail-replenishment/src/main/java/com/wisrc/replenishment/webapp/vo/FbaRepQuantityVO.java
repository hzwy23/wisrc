package com.wisrc.replenishment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class FbaRepQuantityVO {

    @ApiModelProperty(value = "修改补货数量")
    protected int replenishmentQuantity;
    @ApiModelProperty(value = "商品ID")
    private String commodityId;

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public int getReplenishmentQuantity() {
        return replenishmentQuantity;
    }

    public void setReplenishmentQuantity(int replenishmentQuantity) {
        this.replenishmentQuantity = replenishmentQuantity;
    }
}
