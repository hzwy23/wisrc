package com.wisrc.basic.entity;

import io.swagger.annotations.ApiModelProperty;

public class ExemptingNatureAttrEntity {
    @ApiModelProperty(value = "征免性质ID(0--全选，1--一般征税（默认）)", required = true)
    private int exemptingNatureCd;
    @ApiModelProperty(value = "征免性质名称", required = true)
    private String exemptingNatureName;

    public int getExemptingNatureCd() {
        return exemptingNatureCd;
    }

    public void setExemptingNatureCd(int exemptingNatureCd) {
        this.exemptingNatureCd = exemptingNatureCd;
    }

    public String getExemptingNatureName() {
        return exemptingNatureName;
    }

    public void setExemptingNatureName(String exemptingNatureName) {
        this.exemptingNatureName = exemptingNatureName;
    }

    @Override
    public String toString() {
        return "ExemptingNatureAttrEntity{" +
                "exemptingNatureCd=" + exemptingNatureCd +
                ", exemptingNatureName='" + exemptingNatureName + '\'' +
                '}';
    }
}
