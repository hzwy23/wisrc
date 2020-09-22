package com.wisrc.shipment.webapp.entity;

public class ChangeLabelStatusEnity {
    private Integer operationStatusCd;
    private String operationStatusName;


    public String getOperationStatusName() {
        return operationStatusName;
    }

    public void setOperationStatusName(String operationStatusName) {
        this.operationStatusName = operationStatusName;
    }

    public Integer getOperationStatusCd() {
        return operationStatusCd;
    }

    public void setOperationStatusCd(Integer operationStatusCd) {
        this.operationStatusCd = operationStatusCd;
    }
}
