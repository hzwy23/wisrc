package com.wisrc.shipment.webapp.vo;

public class ChangeRemarkDetail {
    private String sectionCode;
    private String oldFnCode;
    private String targetSectionCode;
    private String newFnCode;
    private String goodsCode;
    private Double totalQuantity;
    private String productionBatchNo;
    private Integer lineNum;

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getOldFnCode() {
        return oldFnCode;
    }

    public void setOldFnCode(String oldFnCode) {
        this.oldFnCode = oldFnCode;
    }

    public String getTargetSectionCode() {
        return targetSectionCode;
    }

    public void setTargetSectionCode(String targetSectionCode) {
        this.targetSectionCode = targetSectionCode;
    }

    public String getNewFnCode() {
        return newFnCode;
    }

    public void setNewFnCode(String newFnCode) {
        this.newFnCode = newFnCode;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }


    public String getProductionBatchNo() {
        return productionBatchNo;
    }

    public void setProductionBatchNo(String productionBatchNo) {
        this.productionBatchNo = productionBatchNo;
    }

    public Integer getLineNum() {
        return lineNum;
    }

    public void setLineNum(Integer lineNum) {
        this.lineNum = lineNum;
    }

    public Double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
