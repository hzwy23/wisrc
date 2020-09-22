package com.wisrc.purchase.webapp.entity;

import java.sql.Timestamp;
import java.util.Date;

public class PurchaseReturnInfoNewEntity {
    private String returnBills;
    private String supplierIds;
    private String productName;
    private String skuId;
    private String returnBill;
    private Date createDate;
    private String supplierId;
    private String employeeId;
    private String warehouseId;
    private String orderId;
    private String remark;
    private Integer deleteStatus;
    private String createUser;
    private Timestamp createTime;
    private String modifyUser;
    private Timestamp modifyTime;

    private Date createDateStart;
    private Date createDateEnd;

    private Integer statusCd;
    private Timestamp statusModifyTime;
    private String packWarehouseId;

    public String getReturnBills() {
        return returnBills;
    }

    public void setReturnBills(String returnBills) {
        this.returnBills = returnBills;
    }

    public String getSupplierIds() {
        return supplierIds;
    }

    public void setSupplierIds(String supplierIds) {
        this.supplierIds = supplierIds;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getReturnBill() {
        return returnBill;
    }

    public void setReturnBill(String returnBill) {
        this.returnBill = returnBill;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
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

    public Date getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(Date createDateStart) {
        this.createDateStart = createDateStart;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    public Timestamp getStatusModifyTime() {
        return statusModifyTime;
    }

    public void setStatusModifyTime(Timestamp statusModifyTime) {
        this.statusModifyTime = statusModifyTime;
    }

    public String getPackWarehouseId() {
        return packWarehouseId;
    }

    public void setPackWarehouseId(String packWarehouseId) {
        this.packWarehouseId = packWarehouseId;
    }
}
