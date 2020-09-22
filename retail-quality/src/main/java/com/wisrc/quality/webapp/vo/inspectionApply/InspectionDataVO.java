package com.wisrc.quality.webapp.vo.inspectionApply;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;

public class InspectionDataVO {

    @ApiModelProperty(value = "采购订单号")
    private String orderId;
    @ApiModelProperty(value = "申请人id")
    private String applyPersonId;
    @ApiModelProperty(value = "申请人姓名")
    private String applyPersonName;
    @ApiModelProperty(value = "预计验货时间")
    private Date expectInspectionTime;
    @ApiModelProperty(value = "供应商id")
    private String supplierId;
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
    @ApiModelProperty(value = "供应商地址")
    private String supplierAddr;
    @ApiModelProperty(value = "skuid")
    private String skuId;
    @ApiModelProperty(value = "产品名称")
    private String productNameCN;
    @ApiModelProperty(value = "验货单号")
    private String InspectionId;
    @ApiModelProperty(value = "申请验货数量")
    private int applyInspectionQuantity;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getApplyPersonId() {
        return applyPersonId;
    }

    public void setApplyPersonId(String applyPersonId) {
        this.applyPersonId = applyPersonId;
    }

    public String getApplyPersonName() {
        return applyPersonName;
    }

    public void setApplyPersonName(String applyPersonName) {
        this.applyPersonName = applyPersonName;
    }

    public Date getExpectInspectionTime() {
        return expectInspectionTime;
    }

    public void setExpectInspectionTime(Date expectInspectionTime) {
        this.expectInspectionTime = expectInspectionTime;
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

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getProductNameCN() {
        return productNameCN;
    }

    public void setProductNameCN(String productNameCN) {
        this.productNameCN = productNameCN;
    }

    public String getInspectionId() {
        return InspectionId;
    }

    public void setInspectionId(String inspectionId) {
        InspectionId = inspectionId;
    }

    public int getApplyInspectionQuantity() {
        return applyInspectionQuantity;
    }

    public void setApplyInspectionQuantity(int applyInspectionQuantity) {
        this.applyInspectionQuantity = applyInspectionQuantity;
    }
}
