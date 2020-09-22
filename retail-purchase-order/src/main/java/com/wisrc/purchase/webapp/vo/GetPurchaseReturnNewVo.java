package com.wisrc.purchase.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class GetPurchaseReturnNewVo {
    @ApiModelProperty(value = "退货单")
    private String returnBill;

    @ApiModelProperty(value = "退货开始日期")
    private String createDateStartStr;

    @ApiModelProperty(value = "退货结束日期")
    private String createDateEndStr;

    @ApiModelProperty(value = "供应商编码")
    private String supplierName;

    @ApiModelProperty(value = "操作人处理人")
    private String employeeId;

    @ApiModelProperty(value = "仓库ID")
    private String warehouseId;

    @ApiModelProperty(value = "采购订单ID")
    private String orderId;

    @ApiModelProperty(value = "skuId")
    private String skuId;

    @ApiModelProperty(value = "产品名称")
    private String productName;


    public String getReturnBill() {
        return returnBill;
    }

    public void setReturnBill(String returnBill) {
        this.returnBill = returnBill;
    }

    public String getCreateDateStartStr() {
        return createDateStartStr;
    }

    public void setCreateDateStartStr(String createDateStartStr) {
        this.createDateStartStr = createDateStartStr;
    }

    public String getCreateDateEndStr() {
        return createDateEndStr;
    }

    public void setCreateDateEndStr(String createDateEndStr) {
        this.createDateEndStr = createDateEndStr;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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
