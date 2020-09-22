package com.wisrc.order.webapp.dto.shipment;

import io.swagger.annotations.ApiModelProperty;

public class LogisticsAllowLabelRelEntity {
    @ApiModelProperty(value = "主键ID", hidden = true)
    private String uuid;
    @ApiModelProperty(value = "报价ID", hidden = true)
    private String offerId;
    @ApiModelProperty(value = "标签ID")
    private int labelCd;
    @ApiModelProperty(value = "状态，0--正常，1--已删除")
    private int deleteStatus;
    @ApiModelProperty(value = "标签描述")
    private String labelName;

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

    public int getLabelCd() {
        return labelCd;
    }

    public void setLabelCd(int labelCd) {
        this.labelCd = labelCd;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
