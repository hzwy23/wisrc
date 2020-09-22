package com.wisrc.purchase.webapp.entity;

import java.util.Objects;

public class InspectionTypeAttrEntity {
    private int inspectionTypeCd;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InspectionTypeAttrEntity that = (InspectionTypeAttrEntity) o;
        return inspectionTypeCd == that.inspectionTypeCd &&
                Objects.equals(inspectionTypeDesc, that.inspectionTypeDesc);
    }

    @Override
    public int hashCode() {

        return Objects.hash(inspectionTypeCd, inspectionTypeDesc);
    }
}
