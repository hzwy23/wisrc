package com.wisrc.shipment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class LogisticsOfferEffectiveEntity {
    @ApiModelProperty(value = "主键ID", hidden = true)
    private String uuid;
    @ApiModelProperty(value = "报价ID", hidden = true)
    private String offerId;
    @Min(value = 0, message = "时效开始区间只能为非负数")
    @ApiModelProperty(value = "物流时效开始", required = true)
    @NotNull
    private Integer startDays;
    @Min(value = 0, message = "时效开结束区间只能为非负数")
    @ApiModelProperty(value = "物流时效结束", required = true)
    private Integer endDays;
    @ApiModelProperty(value = "时效备注")
    private String timeRemark;
    @ApiModelProperty(value = "状态，0--正常，1--已删除")
    private int deleteStatus;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public Integer getStartDays() {
        return startDays;
    }

    public void setStartDays(Integer startDays) {
        this.startDays = startDays;
    }

    public Integer getEndDays() {
        return endDays;
    }

    public void setEndDays(Integer endDays) {
        this.endDays = endDays;
    }

    public String getTimeRemark() {
        return timeRemark;
    }

    public void setTimeRemark(String timeRemark) {
        this.timeRemark = timeRemark;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
