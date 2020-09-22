package com.wisrc.quality.webapp.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class InspectionApplyInfoEntity {

    private String inspectionId;
    private String orderId;
    private String employeeId;
    private Date applyDate;
    private Date expectInspectionTime;
    private Integer inspectionTypeCd;
    private String supplierId;
    private String supplierContactUser;
    private String supplierPhone;
    private String supplierAddr;
    private String remark;
    private Timestamp createTime;
    private String createUser;
    private String modifyUser;
    private Timestamp modifyTime;
    private int deleteStatus;

    public String getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getExpectInspectionTime() {
        return expectInspectionTime;
    }

    public void setExpectInspectionTime(Date expectInspectionTime) {
        this.expectInspectionTime = expectInspectionTime;
    }

    public Integer getInspectionTypeCd() {
        return inspectionTypeCd;
    }

    public void setInspectionTypeCd(Integer inspectionTypeCd) {
        this.inspectionTypeCd = inspectionTypeCd;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierContactUser() {
        return supplierContactUser;
    }

    public void setSupplierContactUser(String supplierContactUser) {
        this.supplierContactUser = supplierContactUser;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public String getSupplierAddr() {
        return supplierAddr;
    }

    public void setSupplierAddr(String supplierAddr) {
        this.supplierAddr = supplierAddr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
