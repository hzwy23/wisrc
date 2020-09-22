package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

public class InspectionedExtractAttrEntity {
    @ApiModelProperty(value = "状态id")
    private int inspectionedExtractCd;
    @ApiModelProperty(value = "状态描述")
    private String inspectionedExtractDesc;

    public int getInspectionedExtractCd() {
        return inspectionedExtractCd;
    }

    public void setInspectionedExtractCd(int inspectionedExtractCd) {
        this.inspectionedExtractCd = inspectionedExtractCd;
    }

    public String getInspectionedExtractDesc() {
        return inspectionedExtractDesc;
    }

    public void setInspectionedExtractDesc(String inspectionedExtractDesc) {
        this.inspectionedExtractDesc = inspectionedExtractDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InspectionedExtractAttrEntity that = (InspectionedExtractAttrEntity) o;
        return inspectionedExtractCd == that.inspectionedExtractCd &&
                Objects.equals(inspectionedExtractDesc, that.inspectionedExtractDesc);
    }

    @Override
    public int hashCode() {

        return Objects.hash(inspectionedExtractCd, inspectionedExtractDesc);
    }
}
