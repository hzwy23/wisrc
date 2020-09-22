package com.wisrc.warehouse.webapp.vo;

import com.wisrc.warehouse.webapp.entity.BlitemInfoEntity;

import java.util.List;

public class BlitemInfoVO extends BlitemInfoEntity {
    private String createUserName;
    private String auditUserName;
    private String operationUserName;
    private String remark;
    private List<BlitemRemarkVO> remarks;
    private List<BlitemListVO> blitemListInfos;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<BlitemListVO> getBlitemListInfos() {
        return blitemListInfos;
    }

    public void setBlitemListInfos(List<BlitemListVO> blitemListInfos) {
        this.blitemListInfos = blitemListInfos;
    }

    public List<BlitemRemarkVO> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<BlitemRemarkVO> remarks) {
        this.remarks = remarks;
    }

    public String getOperationUserName() {
        return operationUserName;
    }

    public void setOperationUserName(String operationUserName) {
        this.operationUserName = operationUserName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }
}
