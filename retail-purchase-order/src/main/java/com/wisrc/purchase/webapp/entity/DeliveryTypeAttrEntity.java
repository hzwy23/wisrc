package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("交货状态")
public class DeliveryTypeAttrEntity {
    @ApiModelProperty(value = "交货状态Cd")
    private int deliveryTypeCd;
    @ApiModelProperty(value = "交货状态名称")
    private String deliveryTypeDesc;

    public int getDeliveryTypeCd() {
        return deliveryTypeCd;
    }

    public void setDeliveryTypeCd(int deliveryTypeCd) {
        this.deliveryTypeCd = deliveryTypeCd;
    }

    public String getDeliveryTypeDesc() {
        return deliveryTypeDesc;
    }

    public void setDeliveryTypeDesc(String deliveryTypeDesc) {
        this.deliveryTypeDesc = deliveryTypeDesc;
    }

    @Override
    public String toString() {
        return "DeliveryTypeAttrEntity{" +
                "deliveryTypeCd=" + deliveryTypeCd +
                ", deliveryTypeDesc='" + deliveryTypeDesc + '\'' +
                '}';
    }
}
