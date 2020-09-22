package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class DeclareMskuUnitAttrEntity {

    @ApiModelProperty(value = "报关资料产品单位")
    private Integer mskuUnitCd;

    @ApiModelProperty(value = "报关资料产品单位名称")
    private String mskuUnitName;

    public Integer getMskuUnitCd() {
        return mskuUnitCd;
    }

    public void setMskuUnitCd(Integer mskuUnitCd) {
        this.mskuUnitCd = mskuUnitCd;
    }

    public String getMskuUnitName() {
        return mskuUnitName;
    }

    public void setMskuUnitName(String mskuUnitName) {
        this.mskuUnitName = mskuUnitName;
    }
}
