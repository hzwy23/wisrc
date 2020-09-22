package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 补货单状态码表
 */
public class FbaReplenishmentStatusAttrEntity {
    @ApiModelProperty(value = "补货单状态码id")
    private int statusCd;
    @ApiModelProperty(value = "补货单状态码名称")
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
}
