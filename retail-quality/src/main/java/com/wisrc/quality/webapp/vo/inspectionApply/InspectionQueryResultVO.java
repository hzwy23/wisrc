package com.wisrc.quality.webapp.vo.inspectionApply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class InspectionQueryResultVO {

    @ApiModelProperty(value = "申请单号")
    private String inspectionId;
    @ApiModelProperty(value = "采购订单号")
    private String orderId;
    @ApiModelProperty(value = "申请人id")
    private String employeeId;
    @ApiModelProperty(value = "申请人名称")
    private String employeeName;
    @ApiModelProperty(value = "预计验货日期")
    private Date expectInspectionTime;
    @ApiModelProperty(value = "申请日期")
    private Date applyDate;
    @ApiModelProperty(value = "供应商id")
    private String supplierId;
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
    @ApiModelProperty(value = "供应商地址")
    private String supplierAddr;
    @ApiModelProperty(value = "验货方式")
    private String inspectionTypeDesc;
    @ApiModelProperty(value = "状态")
    private Integer statusCd;
    @ApiModelProperty(value = "状态名称")
    private String statusName;

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

    public String getSupplierAddr() {
        return supplierAddr;
    }

    public void setSupplierAddr(String supplierAddr) {
        this.supplierAddr = supplierAddr;
    }

    public String getInspectionTypeDesc() {
        return inspectionTypeDesc;
    }

    public void setInspectionTypeDesc(String inspectionTypeDesc) {
        this.inspectionTypeDesc = inspectionTypeDesc;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
