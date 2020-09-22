package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(tags = "重量类型码表")
public class WeightTypeAttrEntity {
    @ApiModelProperty(value = "称重类型")
    private int weighTypeCd;
    @ApiModelProperty(value = "称重类型名称")
    private String weighTypeName;

    public int getWeighTypeCd() {
        return weighTypeCd;
    }

    public void setWeighTypeCd(int weighTypeCd) {
        this.weighTypeCd = weighTypeCd;
    }

    public String getWeighTypeName() {
        return weighTypeName;
    }

    public void setWeighTypeName(String weighTypeName) {
        this.weighTypeName = weighTypeName;
    }

    @Override
    public String toString() {
        return "WeightTypeAttrEntity{" +
                "weighTypeCd=" + weighTypeCd +
                ", weighTypeName='" + weighTypeName + '\'' +
                '}';
    }
}
