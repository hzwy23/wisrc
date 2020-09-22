package com.wisrc.warehouse.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class HandmadeStatusEntity {
    @ApiModelProperty("状态编码")
    private Integer statusCd;
    @ApiModelProperty("状态名称")
    private String statusName;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getStatusCd() {

        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }
}
