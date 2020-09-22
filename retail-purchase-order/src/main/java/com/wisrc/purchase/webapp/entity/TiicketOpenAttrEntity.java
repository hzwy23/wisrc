package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "是否开票")
public class TiicketOpenAttrEntity {
    @ApiModelProperty(value = "开票类型")
    private int tiicketOpenCd;
    @ApiModelProperty(value = "开票类型名称")
    private String tiicketOpenDesc;

    public int getTiicketOpenCd() {
        return tiicketOpenCd;
    }

    public void setTiicketOpenCd(int tiicketOpenCd) {
        this.tiicketOpenCd = tiicketOpenCd;
    }

    public String getTiicketOpenDesc() {
        return tiicketOpenDesc;
    }

    public void setTiicketOpenDesc(String tiicketOpenDesc) {
        this.tiicketOpenDesc = tiicketOpenDesc;
    }

    @Override
    public String toString() {
        return "TiicketOpenAttrEntity{" +
                "tiicketOpenCd=" + tiicketOpenCd +
                ", tiicketOpenDesc='" + tiicketOpenDesc + '\'' +
                '}';
    }
}
