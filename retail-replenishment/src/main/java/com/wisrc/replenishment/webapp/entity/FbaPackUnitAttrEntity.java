package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 补货单商品单位码表
 */
public class FbaPackUnitAttrEntity {

    @ApiModelProperty(value = "单位编码")
    private int unitCd;
    @ApiModelProperty(value = "单位描述")
    private String unitDesc;

    public int getUnitCd() {
        return unitCd;
    }

    public void setUnitCd(int unitCd) {
        this.unitCd = unitCd;
    }

    public String getUnitDesc() {
        return unitDesc;
    }

    public void setUnitDesc(String unitDesc) {
        this.unitDesc = unitDesc;
    }
}
