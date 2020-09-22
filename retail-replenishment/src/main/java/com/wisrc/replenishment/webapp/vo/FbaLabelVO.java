package com.wisrc.replenishment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class FbaLabelVO {

    @ApiModelProperty(value = "标签系统唯一标识", hidden = true)
    private String uuid;
    @ApiModelProperty(value = "标签名称")
    private String labelName;
    @ApiModelProperty(value = "标签颜色")
    private String labelColor;
    @ApiModelProperty(value = "标签代码")
    private int labelCd;

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

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

    public String getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }
}
