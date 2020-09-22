package com.wisrc.basic.entity;

import io.swagger.annotations.ApiModelProperty;

public class DeliveryPortAttrEntity {
    @ApiModelProperty(value = "出口口岸ID(空运默认0--全选，海运默认1--深圳)", required = true)
    private int deliveryPortCd;
    @ApiModelProperty(value = "出口口岸名称", required = true)
    private String deliveryPortName;

    public int getDeliveryPortCd() {
        return deliveryPortCd;
    }

    public void setDeliveryPortCd(int deliveryPortCd) {
        this.deliveryPortCd = deliveryPortCd;
    }

    public String getDeliveryPortName() {
        return deliveryPortName;
    }

    public void setDeliveryPortName(String deliveryPortName) {
        this.deliveryPortName = deliveryPortName;
    }

    @Override
    public String toString() {
        return "DeliveryPortAttrEntity{" +
                "deliveryPortCd=" + deliveryPortCd +
                ", deliveryPortName='" + deliveryPortName + '\'' +
                '}';
    }
}
