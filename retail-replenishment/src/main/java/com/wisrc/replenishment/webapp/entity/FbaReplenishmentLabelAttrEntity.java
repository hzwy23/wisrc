package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 补货单商品信息标签码表
 */
public class FbaReplenishmentLabelAttrEntity {

    @ApiModelProperty(value = "标签代码(新增时不需要传，修改时需要传)")
    private int labelCd;
    @ApiModelProperty(value = "标签名称")
    private String labelName;
    @ApiModelProperty(value = "标签颜色")
    private String labelColor;
    @ApiModelProperty(value = "删除标识", hidden = true)
    private int deleteStatus;

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

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
