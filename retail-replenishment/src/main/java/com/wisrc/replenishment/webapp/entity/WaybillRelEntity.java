package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(tags = "补货交运信息")
public class WaybillRelEntity {
    @ApiModelProperty(value = "系统唯一标示")
    private String uuid;
    @ApiModelProperty(value = "FBA补货ID")
    private String fbaReplenishmentId;
    @ApiModelProperty(value = "运单ID")
    private String waybillId;
    @ApiModelProperty(value = "删除标识")
    private int deleteStatus;

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFbaReplenishmentId() {
        return fbaReplenishmentId;
    }

    public void setFbaReplenishmentId(String fbaReplenishmentId) {
        this.fbaReplenishmentId = fbaReplenishmentId;
    }

    public String getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(String waybillId) {
        this.waybillId = waybillId;
    }

    @Override
    public String toString() {
        return "WaybillRelEntity{" +
                "uuid='" + uuid + '\'' +
                ", fbaReplenishmentId='" + fbaReplenishmentId + '\'' +
                ", waybillId='" + waybillId + '\'' +
                ", deleteStatus=" + deleteStatus +
                '}';
    }
}
