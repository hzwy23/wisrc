package com.wisrc.warehouse.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class ProcessTaskStatusAttrEntity {
    @ApiModelProperty(value = "状态编码")
    private int statusCd;
    @ApiModelProperty(value = "状态名称")
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
