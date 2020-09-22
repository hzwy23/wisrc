package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(tags = "报关类型码表")
public class CustomsAttrEntity {
    @ApiModelProperty(value = "报关类型")
    private int customsTypeCd;
    @ApiModelProperty(value = "报关类型名称")
    private String customsTypeName;

    public int getCustomsTypeCd() {
        return customsTypeCd;
    }

    public void setCustomsTypeCd(int customsTypeCd) {
        this.customsTypeCd = customsTypeCd;
    }

    public String getCustomsTypeName() {
        return customsTypeName;
    }

    public void setCustomsTypeName(String customsTypeName) {
        this.customsTypeName = customsTypeName;
    }

    @Override
    public String toString() {
        return "CustomsAttrEntity{" +
                "customsTypeCd=" + customsTypeCd +
                ", customsTypeName='" + customsTypeName + '\'' +
                '}';
    }
}
