package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

@Api(tags = "供应商交期及报价信息")
public class SupplierDateOfferEntity {
    @ApiModelProperty(value = "报价单号")
    private String supplierOfferId;
    @ApiModelProperty(value = "状态")
    private int statusCd;
    @ApiModelProperty(value = "采购员")
    private String employeeId;
    @ApiModelProperty(value = "库存SKU")
    private String skuId;
    @ApiModelProperty(value = "供应商ID")
    private String supplierId;
    @ApiModelProperty(value = "第1批交期")
    private int firstDelivery;
    @ApiModelProperty(value = "通用交期")
    private int generalDelivery;
    @ApiModelProperty(value = "不含税单价")
    private double unitPriceWithoutTax;
    @ApiModelProperty(value = "最少起订量")
    private int minimum;
    @ApiModelProperty(value = "运输时间-国内")
    private int haulageDays;
    @ApiModelProperty(value = "交期分解")
    private String deliveryPart;
    @ApiModelProperty(value = "交期优化方案")
    private String deliveryOptimize;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "创建人")
    private String createUser;
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;
    @ApiModelProperty(value = "修改人")
    private String modifyUser;
    @ApiModelProperty(value = "修改时间")
    private Timestamp modifyTime;
    @ApiModelProperty(value = "删除标识")
    private int deleteStatus;

    public String getSupplierOfferId() {
        return supplierOfferId;
    }

    public void setSupplierOfferId(String supplierOfferId) {
        this.supplierOfferId = supplierOfferId;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public int getFirstDelivery() {
        return firstDelivery;
    }

    public void setFirstDelivery(int firstDelivery) {
        this.firstDelivery = firstDelivery;
    }

    public int getGeneralDelivery() {
        return generalDelivery;
    }

    public void setGeneralDelivery(int generalDelivery) {
        this.generalDelivery = generalDelivery;
    }

    public double getUnitPriceWithoutTax() {
        return unitPriceWithoutTax;
    }

    public void setUnitPriceWithoutTax(double unitPriceWithoutTax) {
        this.unitPriceWithoutTax = unitPriceWithoutTax;
    }

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getHaulageDays() {
        return haulageDays;
    }

    public void setHaulageDays(int haulageDays) {
        this.haulageDays = haulageDays;
    }

    public String getDeliveryPart() {
        return deliveryPart;
    }

    public void setDeliveryPart(String deliveryPart) {
        this.deliveryPart = deliveryPart;
    }

    public String getDeliveryOptimize() {
        return deliveryOptimize;
    }

    public void setDeliveryOptimize(String deliveryOptimize) {
        this.deliveryOptimize = deliveryOptimize;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @Override
    public String toString() {
        return "SupplierDateOfferEntity{" +
                "supplierOfferId='" + supplierOfferId + '\'' +
                ", statusCd=" + statusCd +
                ", employeeId='" + employeeId + '\'' +
                ", skuId='" + skuId + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", firstDelivery=" + firstDelivery +
                ", generalDelivery=" + generalDelivery +
                ", unitPriceWithoutTax=" + unitPriceWithoutTax +
                ", minimum=" + minimum +
                ", haulageDays=" + haulageDays +
                ", deliveryPart='" + deliveryPart + '\'' +
                ", deliveryOptimize='" + deliveryOptimize + '\'' +
                ", remark='" + remark + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyTime=" + modifyTime +
                ", deleteStatus=" + deleteStatus +
                '}';
    }
}
