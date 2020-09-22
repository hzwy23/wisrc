package com.wisrc.purchase.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class GetPurchaseRejectionNewVO {
    @ApiModelProperty(value = "采购拒收单号")
    private String rejectionId;

    @ApiModelProperty(value = "开始日期")
    private String rejectionDateStartStr;

    @ApiModelProperty(value = "结束日期")
    private String rejectionDateEndStr;

    @ApiModelProperty(value = "供应商编码")
    private String supplierName;

    @ApiModelProperty(value = "验货申请/提货单号")
    private String inspectionId;

    @ApiModelProperty(value = "处理人")
    private String handleUser;

    @ApiModelProperty(value = "拒收单状态")
    private Integer statusCd;

    @ApiModelProperty(value = "采购订单ID")
    private String orderId;

    @ApiModelProperty(value = "skuId")
    private String skuId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    public String getRejectionId() {
        return rejectionId;
    }

    public void setRejectionId(String rejectionId) {
        this.rejectionId = rejectionId;
    }

    public String getRejectionDateStartStr() {
        return rejectionDateStartStr;
    }

    public void setRejectionDateStartStr(String rejectionDateStartStr) {
        this.rejectionDateStartStr = rejectionDateStartStr;
    }

    public String getRejectionDateEndStr() {
        return rejectionDateEndStr;
    }

    public void setRejectionDateEndStr(String rejectionDateEndStr) {
        this.rejectionDateEndStr = rejectionDateEndStr;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(String handleUser) {
        this.handleUser = handleUser;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
