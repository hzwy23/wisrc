package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(tags = "运单异常状态码表")
public class ExceptionTypeAttrEntity {
    @ApiModelProperty(value = "异常ID")
    private int exceptionTypeCd;
    @ApiModelProperty(value = "异常描述名称")
    private String exceptionTypeDesc;

    public int getExceptionTypeCd() {
        return exceptionTypeCd;
    }

    public void setExceptionTypeCd(int exceptionTypeCd) {
        this.exceptionTypeCd = exceptionTypeCd;
    }

    public String getExceptionTypeDesc() {
        return exceptionTypeDesc;
    }

    public void setExceptionTypeDesc(String exceptionTypeDesc) {
        this.exceptionTypeDesc = exceptionTypeDesc;
    }

    @Override
    public String toString() {
        return "ExceptionTypeAttrEntity{" +
                "exceptionTypeCd=" + exceptionTypeCd +
                ", exceptionTypeDesc='" + exceptionTypeDesc + '\'' +
                '}';
    }
}
