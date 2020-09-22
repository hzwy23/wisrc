package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(tags = "在光标位置插入字段码表")
public class DynamicFieldsAttrEntity {
    @ApiModelProperty(value = "在光标位置插入字段CD")
    private int dynamicFieldsCd;
    @ApiModelProperty(value = "在光标位置插入字段名称")
    private String dynamicFieldsDesc;

    public int getDynamicFieldsCd() {
        return dynamicFieldsCd;
    }

    public void setDynamicFieldsCd(int dynamicFieldsCd) {
        this.dynamicFieldsCd = dynamicFieldsCd;
    }

    public String getDynamicFieldsDesc() {
        return dynamicFieldsDesc;
    }

    public void setDynamicFieldsDesc(String dynamicFieldsDesc) {
        this.dynamicFieldsDesc = dynamicFieldsDesc;
    }

    @Override
    public String toString() {
        return "DynamicFieldsAttrEntity{" +
                "dynamicFieldsCd=" + dynamicFieldsCd +
                ", dynamicFieldsDesc='" + dynamicFieldsDesc + '\'' +
                '}';
    }
}
