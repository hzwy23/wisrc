package com.wisrc.basic.entity;

import io.swagger.annotations.ApiModelProperty;

public class ShipmentStatusAttrEntity {
    @ApiModelProperty(value = "物流商状态ID(1--正常（默认）,2--已删除)", required = true)
    private int statusCd;
    @ApiModelProperty(value = "物流商状态名称", required = true)
    private String statusName;

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return "ShipmentStatusAttrEntity{" +
                "statusCd=" + statusCd +
                ", statusName='" + statusName + '\'' +
                '}';
    }
}

