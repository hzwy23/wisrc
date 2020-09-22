package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "报关类型")
public class CustomsTypeAtrEntity {
    @ApiModelProperty(value = "报关类型cd")
    private int customsTypeCd;
    @ApiModelProperty(value = "报关类型名称")
    private String customsTypeDesc;

    public int getCustomsTypeCd() {
        return customsTypeCd;
    }

    public void setCustomsTypeCd(int customsTypeCd) {
        this.customsTypeCd = customsTypeCd;
    }

    public String getCustomsTypeDesc() {
        return customsTypeDesc;
    }

    public void setCustomsTypeDesc(String customsTypeDesc) {
        this.customsTypeDesc = customsTypeDesc;
    }

    @Override
    public String toString() {
        return "CustomsTypeAtrEntity{" +
                "customsTypeCd=" + customsTypeCd +
                ", customsTypeDesc='" + customsTypeDesc + '\'' +
                '}';
    }
}
