package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Api(tags = "订单详情信息")
public class OrderBasisInfoEntity {
    @ApiModelProperty(value = "采购订单号）")
    private String orderId;
    @ApiModelProperty(value = "单据日期）", required = false)
    private Date billDate;
    @ApiModelProperty(value = "供应商名称）")
    private String supplierId;
    @ApiModelProperty(value = "供应商名称）")
    private String supplierName;
    @ApiModelProperty(value = "采购员）")
    private String employeeId;
    @ApiModelProperty(value = "采购员名称）")
    private String employeeName;
    @ApiModelProperty(value = "不含税金额）")
    private double amountWithoutTax;
    @ApiModelProperty(value = "含税金额）")
    private double amountWithTax;
    @ApiModelProperty(value = "是否开票类型）")
    private int tiicketOpenCd;
    @ApiModelProperty(value = "报关状态）")
    private int customsTypeCd;
    @ApiModelProperty(value = "付款条款）")
    private String paymentProvision;
    @ApiModelProperty(value = "交货状态）")
    private int deliveryTypeCd;
    @ApiModelProperty(value = "单据录入人）")
    private String createUser;
    @ApiModelProperty(value = "单据录入日期）", required = false)
    private Timestamp createTime;
    @ApiModelProperty(value = "供应商联系人）")
    private String supplierPeople;
    @ApiModelProperty(value = "供应商电话）")
    private String supplierPhone;
    @ApiModelProperty(value = "供应商传真）")
    private String supplierFax;
    @ApiModelProperty(value = "运费）")
    private double freight;
    @ApiModelProperty(value = "定金率）")
    private double depositRate;
    @ApiModelProperty(value = "订单状态）")
    private int orderStatus;
    @ApiModelProperty(value = "合计）")
    private int totalAmount;
    @ApiModelProperty(value = "备注")
    private String remark;

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
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

    public double getAmountWithoutTax() {
        return amountWithoutTax;
    }

    public void setAmountWithoutTax(double amountWithoutTax) {
        this.amountWithoutTax = amountWithoutTax;
    }

    public double getAmountWithTax() {
        return amountWithTax;
    }

    public void setAmountWithTax(double amountWithTax) {
        this.amountWithTax = amountWithTax;
    }

    public int getTiicketOpenCd() {
        return tiicketOpenCd;
    }

    public void setTiicketOpenCd(int tiicketOpenCd) {
        this.tiicketOpenCd = tiicketOpenCd;
    }

    public int getCustomsTypeCd() {
        return customsTypeCd;
    }

    public void setCustomsTypeCd(int customsTypeCd) {
        this.customsTypeCd = customsTypeCd;
    }

    public String getPaymentProvision() {
        return paymentProvision;
    }

    public void setPaymentProvision(String paymentProvision) {
        this.paymentProvision = paymentProvision;
    }

    public int getDeliveryTypeCd() {
        return deliveryTypeCd;
    }

    public void setDeliveryTypeCd(int deliveryTypeCd) {
        this.deliveryTypeCd = deliveryTypeCd;
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

    public String getSupplierPeople() {
        return supplierPeople;
    }

    public void setSupplierPeople(String supplierPeople) {
        this.supplierPeople = supplierPeople;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public String getSupplierFax() {
        return supplierFax;
    }

    public void setSupplierFax(String supplierFax) {
        this.supplierFax = supplierFax;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public double getDepositRate() {
        return depositRate;
    }

    public void setDepositRate(double depositRate) {
        this.depositRate = depositRate;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "OrderBasisInfoEntity{" +
                "orderId='" + orderId + '\'' +
                ", billDate=" + billDate +
                ", supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", amountWithoutTax=" + amountWithoutTax +
                ", amountWithTax=" + amountWithTax +
                ", tiicketOpenCd=" + tiicketOpenCd +
                ", customsTypeCd=" + customsTypeCd +
                ", paymentProvision='" + paymentProvision + '\'' +
                ", deliveryTypeCd=" + deliveryTypeCd +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", supplierPeople='" + supplierPeople + '\'' +
                ", supplierPhone='" + supplierPhone + '\'' +
                ", supplierFax='" + supplierFax + '\'' +
                ", freight=" + freight +
                ", depositRate=" + depositRate +
                ", orderStatus=" + orderStatus +
                ", totalAmount=" + totalAmount +
                ", remark='" + remark + '\'' +
                '}';
    }
}
