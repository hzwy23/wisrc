package com.wisrc.basic.entity;

import io.swagger.annotations.ApiModelProperty;

public class ShipmentTypeAttrEntity {
    @ApiModelProperty(value = "物流商类型ID(1--官方渠道，2--代理渠道)", required = true)
    private int shipmentType;
    @ApiModelProperty(value = "物流商类型描述", required = true)
    private String shipmentTypeDesc;

    public int getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(int shipmentType) {
        this.shipmentType = shipmentType;
    }

    public String getShipmentTypeDesc() {
        return shipmentTypeDesc;
    }

    public void setShipmentTypeDesc(String shipmentTypeDesc) {
        this.shipmentTypeDesc = shipmentTypeDesc;
    }
}
