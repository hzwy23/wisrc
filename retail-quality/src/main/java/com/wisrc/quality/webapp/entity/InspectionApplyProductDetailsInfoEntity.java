package com.wisrc.quality.webapp.entity;

public class InspectionApplyProductDetailsInfoEntity {

    private String inspectionProductId;

    private String inspectionId;

    private String skuId;

    private int applyInspectionQuantity;

    private int inspectionQuantity;

    private int qualifiedQuantity;

    private int unqualifiedQuantity;

    private int statusCd;

    private String statusName;

    public String getInspectionProductId() {
        return inspectionProductId;
    }

    public void setInspectionProductId(String inspectionProductId) {
        this.inspectionProductId = inspectionProductId;
    }

    public String getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public int getApplyInspectionQuantity() {
        return applyInspectionQuantity;
    }

    public void setApplyInspectionQuantity(int applyInspectionQuantity) {
        this.applyInspectionQuantity = applyInspectionQuantity;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public int getInspectionQuantity() {
        return inspectionQuantity;
    }

    public void setInspectionQuantity(int inspectionQuantity) {
        this.inspectionQuantity = inspectionQuantity;
    }

    public int getQualifiedQuantity() {
        return qualifiedQuantity;
    }

    public void setQualifiedQuantity(int qualifiedQuantity) {
        this.qualifiedQuantity = qualifiedQuantity;
    }

    public int getUnqualifiedQuantity() {
        return unqualifiedQuantity;
    }

    public void setUnqualifiedQuantity(int unqualifiedQuantity) {
        this.unqualifiedQuantity = unqualifiedQuantity;
    }
}
