package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class SumNum {
    @ApiModelProperty(value = "补货数量")
    private Integer replenishmentNumber;
    @ApiModelProperty(value = "发货数量")
    private Integer deliveryNumber;
    @ApiModelProperty(value = "签收数量")
    private Integer signNumber;

    public Integer getReplenishmentNumber() {
        return replenishmentNumber;
    }

    public void setReplenishmentNumber(Integer replenishmentNumber) {
        this.replenishmentNumber = replenishmentNumber;
    }

    public Integer getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(Integer deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public Integer getSignNumber() {
        return signNumber;
    }

    public void setSignNumber(Integer signNumber) {
        this.signNumber = signNumber;
    }
}
