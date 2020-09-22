package com.wisrc.basic.entity;

import io.swagger.annotations.ApiModelProperty;

public class ExemptionModeAttrEntity {
    @ApiModelProperty(value = "征免模式ID(0--全选,1--照章征税（默认）,2--折半征税,3--全免)", required = true)
    private int exemptionModeCd;
    @ApiModelProperty(value = "征免模式名称", required = true)
    private String exemptionModeName;

    public int getExemptionModeCd() {
        return exemptionModeCd;
    }

    public void setExemptionModeCd(int exemptionModeCd) {
        this.exemptionModeCd = exemptionModeCd;
    }

    public String getExemptionModeName() {
        return exemptionModeName;
    }

    public void setExemptionModeName(String exemptionModeName) {
        this.exemptionModeName = exemptionModeName;
    }

    @Override
    public String toString() {
        return "ExemptionModeAttrEntity{" +
                "exemptionModeCd=" + exemptionModeCd +
                ", exemptionModeName='" + exemptionModeName + '\'' +
                '}';
    }
}
