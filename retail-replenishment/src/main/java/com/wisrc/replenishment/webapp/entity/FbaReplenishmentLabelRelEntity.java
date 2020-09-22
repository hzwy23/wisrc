package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 补货单商品标签信息表
 */
public class FbaReplenishmentLabelRelEntity {

    @ApiModelProperty(value = "系统唯一标识", hidden = true)
    private String uuid;
    @ApiModelProperty(value = "补货单ID", position = 1, hidden = true)
    private String fbaReplenishmentId;
    @ApiModelProperty(value = "标签代码", position = 2)
    private int labelCd;
    @ApiModelProperty(value = "标签名称", position = 3)
    private String labelName;
    @ApiModelProperty(value = "删除标识", hidden = true)
    private int deleteStatus;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getLabelCd() {
        return labelCd;
    }

    public void setLabelCd(int labelCd) {
        this.labelCd = labelCd;
    }

    public String getFbaReplenishmentId() {
        return fbaReplenishmentId;
    }

    public void setFbaReplenishmentId(String fbaReplenishmentId) {
        this.fbaReplenishmentId = fbaReplenishmentId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
