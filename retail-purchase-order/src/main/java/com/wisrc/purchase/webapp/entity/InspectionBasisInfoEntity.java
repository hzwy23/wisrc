package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class InspectionBasisInfoEntity {
    @ApiModelProperty(value = "申请单号")
    private String inspectionId;
    @ApiModelProperty(value = "采购订单号")
    private String orderId;
    @ApiModelProperty(value = "申请人")
    private String employeeId;
    @ApiModelProperty(value = "申请日期")
    private Date applyDate;
    @ApiModelProperty(value = "预计验货时间")
    private Timestamp expectInspectionTime;
    @ApiModelProperty(value = "运费")
    private Double freight;
    @ApiModelProperty(value = "验货方式id")
    private Integer inspectionTypeCd;
    @ApiModelProperty(value = "运费分摊原则id")
    private Integer amrtTypeCd;
    @ApiModelProperty(value = "是否从已经验证获取中提取")
    private Integer inspectionedExtractCd;
    @ApiModelProperty(value = "供应商id")
    private String supplierId;
    @ApiModelProperty(value = "供应商联系人")
    private String supplierContactUser;
    @ApiModelProperty(value = "供应商电话")
    private String supplierPhone;
    @ApiModelProperty(value = "供应商地址")
    private String supplierAddr;
    @ApiModelProperty(value = "备注信息")
    private String remark;
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;
    @ApiModelProperty(value = "创建人")
    private String createUser;

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

    public Timestamp getExpectInspectionTime() {
        return expectInspectionTime;
    }

    public void setExpectInspectionTime(Timestamp expectInspectionTime) {
        this.expectInspectionTime = expectInspectionTime;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Integer getInspectionTypeCd() {
        return inspectionTypeCd;
    }

    public void setInspectionTypeCd(Integer inspectionTypeCd) {
        this.inspectionTypeCd = inspectionTypeCd;
    }

    public Integer getAmrtTypeCd() {
        return amrtTypeCd;
    }

    public void setAmrtTypeCd(Integer amrtTypeCd) {
        this.amrtTypeCd = amrtTypeCd;
    }

    public Integer getInspectionedExtractCd() {
        return inspectionedExtractCd;
    }

    public void setInspectionedExtractCd(Integer inspectionedExtractCd) {
        this.inspectionedExtractCd = inspectionedExtractCd;
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

    @Override
    public String toString() {
        return "InspectionBasisInfoEntity{" +
                "inspectionId='" + inspectionId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", applyDate=" + applyDate +
                ", expectInspectionTime=" + expectInspectionTime +
                ", freight=" + freight +
                ", inspectionTypeCd=" + inspectionTypeCd +
                ", amrtTypeCd=" + amrtTypeCd +
                ", inspectionedExtractCd=" + inspectionedExtractCd +
                ", supplierId='" + supplierId + '\'' +
                ", supplierContactUser='" + supplierContactUser + '\'' +
                ", supplierPhone='" + supplierPhone + '\'' +
                ", supplierAddr='" + supplierAddr + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InspectionBasisInfoEntity that = (InspectionBasisInfoEntity) o;
        return Objects.equals(inspectionId, that.inspectionId) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(applyDate, that.applyDate) &&
                Objects.equals(expectInspectionTime, that.expectInspectionTime) &&
                Objects.equals(freight, that.freight) &&
                Objects.equals(inspectionTypeCd, that.inspectionTypeCd) &&
                Objects.equals(amrtTypeCd, that.amrtTypeCd) &&
                Objects.equals(inspectionedExtractCd, that.inspectionedExtractCd) &&
                Objects.equals(supplierId, that.supplierId) &&
                Objects.equals(supplierContactUser, that.supplierContactUser) &&
                Objects.equals(supplierPhone, that.supplierPhone) &&
                Objects.equals(supplierAddr, that.supplierAddr) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(createUser, that.createUser);
    }

    @Override
    public int hashCode() {

        return Objects.hash(inspectionId, orderId, employeeId, applyDate, expectInspectionTime, freight, inspectionTypeCd, amrtTypeCd, inspectionedExtractCd, supplierId, supplierContactUser, supplierPhone, supplierAddr, remark, createTime, createUser);
    }
}
