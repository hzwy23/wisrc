package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(tags = "合作状态码表")
public class TeamStatusAttrEntity {
    @ApiModelProperty(value = "合作状态CD")
    private int statusCd;
    @ApiModelProperty(value = "合作状态名称")
    private String statusDesc;

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}
