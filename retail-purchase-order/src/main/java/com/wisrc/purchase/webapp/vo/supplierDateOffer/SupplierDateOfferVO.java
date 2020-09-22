package com.wisrc.purchase.webapp.vo.supplierDateOffer;

import com.wisrc.purchase.webapp.entity.SupplierDateOfferEntity;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

public class SupplierDateOfferVO {
    @ApiModelProperty(value = "报价单号(新增时候不传入)")
    private String supplierOfferId;
    @ApiModelProperty(value = "状态")
    private int statusCd;
    @ApiModelProperty(value = "采购员ID")
    private String employeeId;
    @ApiModelProperty(value = "采购员名称(用于查询，新增修改不传)")
    private String employeeName;
    @ApiModelProperty(value = "库存SKU")
    private String skuId;
    @ApiModelProperty(value = "产品中文名(用于查询，新增修改不传)")
    private String skuNameZh;
    @ApiModelProperty(value = "供应商ID")
    private String supplierId;
    @ApiModelProperty(value = "供应商名称(用于查询，新增修改不传)")
    private String supplierName;
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
    @ApiModelProperty(value = "修改人(用于查询，新增修改不传)")
    private String modifyUser;
    @ApiModelProperty(value = "修改时间(用于查询，新增修改不传)")
    private String modifyTime;

    public static final SupplierDateOfferVO toVO(SupplierDateOfferEntity ele) {
        SupplierDateOfferVO vo = new SupplierDateOfferVO();
        BeanUtils.copyProperties(ele, vo);
        return vo;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getSkuNameZh() {
        return skuNameZh;
    }

    public void setSkuNameZh(String skuNameZh) {
        this.skuNameZh = skuNameZh;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

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


    @Override
    public String toString() {
        return "SupplierDateOfferVO{" +
                "supplierOfferId='" + supplierOfferId + '\'' +
                ", statusCd=" + statusCd +
                ", employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", skuId='" + skuId + '\'' +
                ", skuNameZh='" + skuNameZh + '\'' +
                ", supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", firstDelivery=" + firstDelivery +
                ", generalDelivery=" + generalDelivery +
                ", unitPriceWithoutTax=" + unitPriceWithoutTax +
                ", minimum=" + minimum +
                ", haulageDays=" + haulageDays +
                ", deliveryPart='" + deliveryPart + '\'' +
                ", deliveryOptimize='" + deliveryOptimize + '\'' +
                ", remark='" + remark + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                '}';
    }
}
