package com.wisrc.purchase.webapp.entity;

import java.util.Date;

public class PurchaseRejectionInfoNewEntity {
    private String rejectionId;
    private Date rejectionDate;
    private Date rejectionDateStart;
    private Date rejectionDateEnd;
    private String handleUser;
    private String inspectionId;
    private String supplierCd;
    private String supplierDeliveryNum;
    private String remark;
    private Integer deleteStatus;
    private String orderId;
    private String createUser;
    private String createTime;
    private String modifyUser;
    private String modifyTime;
    private Integer statusCd;
    private String statusModifyTime;
    private String rejectBills;
    private String supplierIds;

    public String getRejectionId() {
        return rejectionId;
    }

    public void setRejectionId(String rejectionId) {
        this.rejectionId = rejectionId;
    }

    public Date getRejectionDate() {
        return rejectionDate;
    }

    public void setRejectionDate(Date rejectionDate) {
        this.rejectionDate = rejectionDate;
    }

    public Date getRejectionDateStart() {
        return rejectionDateStart;
    }

    public void setRejectionDateStart(Date rejectionDateStart) {
        this.rejectionDateStart = rejectionDateStart;
    }

    public Date getRejectionDateEnd() {
        return rejectionDateEnd;
    }

    public void setRejectionDateEnd(Date rejectionDateEnd) {
        this.rejectionDateEnd = rejectionDateEnd;
    }

    public String getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(String handleUser) {
        this.handleUser = handleUser;
    }

    public String getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getSupplierCd() {
        return supplierCd;
    }

    public void setSupplierCd(String supplierCd) {
        this.supplierCd = supplierCd;
    }

    public String getSupplierDeliveryNum() {
        return supplierDeliveryNum;
    }

    public void setSupplierDeliveryNum(String supplierDeliveryNum) {
        this.supplierDeliveryNum = supplierDeliveryNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    public String getStatusModifyTime() {
        return statusModifyTime;
    }

    public void setStatusModifyTime(String statusModifyTime) {
        this.statusModifyTime = statusModifyTime;
    }

    public String getRejectBills() {
        return rejectBills;
    }

    public void setRejectBills(String rejectBills) {
        this.rejectBills = rejectBills;
    }

    public String getSupplierIds() {
        return supplierIds;
    }

    public void setSupplierIds(String supplierIds) {
        this.supplierIds = supplierIds;
    }
}
