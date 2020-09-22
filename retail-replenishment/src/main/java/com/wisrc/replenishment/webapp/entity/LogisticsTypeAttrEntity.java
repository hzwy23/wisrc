package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(tags = "物流状态码表")
public class LogisticsTypeAttrEntity {
    @ApiModelProperty(value = "物流状态")
    private int logisticsTypeCd;
    @ApiModelProperty(value = "物流状态名称")
    private String logisticsTypeName;

    public int getLogisticsTypeCd() {
        return logisticsTypeCd;
    }

    public void setLogisticsTypeCd(int logisticsTypeCd) {
        this.logisticsTypeCd = logisticsTypeCd;
    }

    public String getLogisticsTypeName() {
        return logisticsTypeName;
    }

    public void setLogisticsTypeName(String logisticsTypeName) {
        this.logisticsTypeName = logisticsTypeName;
    }

    @Override
    public String toString() {
        return "LogisticsTypeAttr{" +
                "logisticsTypeCd=" + logisticsTypeCd +
                ", logisticsTypeName='" + logisticsTypeName + '\'' +
                '}';
    }
}
