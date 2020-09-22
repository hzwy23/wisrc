package com.wisrc.quality.webapp.vo.inspectionApply;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * 查看明细时数据
 */
public class InspectionDetailVO {

    @ApiModelProperty(value = "申请单号")
    private String inspectionId;
    @ApiModelProperty(value = "采购订单号")
    private String orderId;
    @ApiModelProperty(value = "申请人id")
    private String employeeId;
    @ApiModelProperty(value = "申请名称")
    private String employeeName;
    @ApiModelProperty(value = "预计验货日期")
    private Date expectInspectionTime;
    @ApiModelProperty(value = "申请日期")
    private Date applyDate;
    @ApiModelProperty(value = "供应商id")
    private String supplierId;
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
    @ApiModelProperty(value = "供应商联系人")
    private String supplierContactUser;
    @ApiModelProperty(value = "供应商地址")
    private String supplierAddr;
    @ApiModelProperty(value = "供应商电话")
    private String supplierPhone;
    @ApiModelProperty(value = "验货方式编码")
    private String inspectionTypeCd;
    @ApiModelProperty(value = "验货方式描述")
    private String inspectionTypeDesc;
    @ApiModelProperty(value = "备注")
    private String remark;

    private List<InspectionProductDetaiVO> productList;

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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Date getExpectInspectionTime() {
        return expectInspectionTime;
    }

    public void setExpectInspectionTime(Date expectInspectionTime) {
        this.expectInspectionTime = expectInspectionTime;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierContactUser() {
        return supplierContactUser;
    }

    public void setSupplierContactUser(String supplierContactUser) {
        this.supplierContactUser = supplierContactUser;
    }

    public String getSupplierAddr() {
        return supplierAddr;
    }

    public void setSupplierAddr(String supplierAddr) {
        this.supplierAddr = supplierAddr;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public String getInspectionTypeCd() {
        return inspectionTypeCd;
    }

    public void setInspectionTypeCd(String inspectionTypeCd) {
        this.inspectionTypeCd = inspectionTypeCd;
    }

    public String getInspectionTypeDesc() {
        return inspectionTypeDesc;
    }

    public void setInspectionTypeDesc(String inspectionTypeDesc) {
        this.inspectionTypeDesc = inspectionTypeDesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<InspectionProductDetaiVO> getProductList() {
        return productList;
    }

    public void setProductList(List<InspectionProductDetaiVO> productList) {
        this.productList = productList;
    }
}
