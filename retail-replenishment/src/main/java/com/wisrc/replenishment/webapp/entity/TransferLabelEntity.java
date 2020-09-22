package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class TransferLabelEntity {
    @ApiModelProperty("标签编码")
    private Integer labelCd;
    @ApiModelProperty("标签名称")
    private String labelName;
    @ApiModelProperty("标签颜色")
    private String labelColor;
    @ApiModelProperty("删除标识")
    private int deleteStatus;

    public Integer getLabelCd() {
        return labelCd;
    }

    public void setLabelCd(Integer labelCd) {
        this.labelCd = labelCd;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
