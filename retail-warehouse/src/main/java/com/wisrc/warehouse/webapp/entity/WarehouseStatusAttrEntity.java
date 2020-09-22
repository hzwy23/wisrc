package com.wisrc.warehouse.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class WarehouseStatusAttrEntity {
    @ApiModelProperty(value = "仓库状态编码", required = true)
    private int statusCd;
    @ApiModelProperty(value = "仓库状态名称", required = true)
    private String statusName;

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getStatusDesc() {
        return statusName;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusName = statusDesc;
    }

}
