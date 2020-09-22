package com.wisrc.replenishment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 设置标签时需要接受的实体类
 */
public class FbaSetLabelVO {

    @ApiModelProperty(value = "补货单Id", position = 1)
    private String fbaReplenishmentId;
    @ApiModelProperty(value = "标签代码", position = 2)
    private int labelCd;
    @ApiModelProperty(value = "标签名称", position = 3)
    private String labelName;
    @ApiModelProperty(value = "标签颜色", position = 4)
    private String labelColor;

    public String getFbaReplenishmentId() {
        return fbaReplenishmentId;
    }

    public void setFbaReplenishmentId(String fbaReplenishmentId) {
        this.fbaReplenishmentId = fbaReplenishmentId;
    }

    public int getLabelCd() {
        return labelCd;
    }

    public void setLabelCd(int labelCd) {
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
}
