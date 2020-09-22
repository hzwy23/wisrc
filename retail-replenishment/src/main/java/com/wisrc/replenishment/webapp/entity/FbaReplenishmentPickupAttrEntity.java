package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 补货单提货方式状态码表
 */
public class FbaReplenishmentPickupAttrEntity {

    @ApiModelProperty(value = "提货方式编码")
    private int pickupTypeCd;
    @ApiModelProperty(value = "提货方式名称")
    private String pickupTypeName;

    public int getPickupTypeCd() {
        return pickupTypeCd;
    }

    public void setPickupTypeCd(int pickupTypeCd) {
        this.pickupTypeCd = pickupTypeCd;
    }

    public String getPickupTypeName() {
        return pickupTypeName;
    }

    public void setPickupTypeName(String pickupTypeName) {
        this.pickupTypeName = pickupTypeName;
    }
}
