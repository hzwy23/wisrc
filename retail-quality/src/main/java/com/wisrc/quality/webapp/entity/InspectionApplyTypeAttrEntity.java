package com.wisrc.quality.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class InspectionApplyTypeAttrEntity {

    @ApiModelProperty(value = "验货方式编码")
    private int inspectionTypeCd;
    @ApiModelProperty(value = "验货方式描述")
    private String inspectionTypeDesc;

    public int getInspectionTypeCd() {
        return inspectionTypeCd;
    }

    public void setInspectionTypeCd(int inspectionTypeCd) {
        this.inspectionTypeCd = inspectionTypeCd;
    }

    public String getInspectionTypeDesc() {
        return inspectionTypeDesc;
    }

    public void setInspectionTypeDesc(String inspectionTypeDesc) {
        this.inspectionTypeDesc = inspectionTypeDesc;
    }
}
