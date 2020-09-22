package com.wisrc.purchase.webapp.vo;

import com.wisrc.purchase.webapp.entity.ProductMachineInfoEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class EntryWarehouseExportVo {
    @ApiModelProperty(value = "采购入库单号")
    private String entryId;
    @ApiModelProperty(value = "入库日期")
    private String entryTime;
    @ApiModelProperty(value = "入库人ID")
    private String entryUser;
    @ApiModelProperty(value = "入库人名称")
    private String employeeName;
    @ApiModelProperty(value = "仓库ID")
    private String warehouseId;
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;
    @ApiModelProperty(value = "验货申请/提货单号")
    private String inspectionId;
    @ApiModelProperty(value = "供应商编号")
    private String supplierCd;
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
    @ApiModelProperty(value = "供应商送货单号")
    private String supplierDeliveryNum;
    @ApiModelProperty(value = "采购订单号")
    private String orderId;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "包材仓库")
    private String packWarehouseId;
    @ApiModelProperty(value = "包材仓库")
    private String packWarehouseName;
    @ApiModelProperty(value = "SKU")
    private String skuId;
    @ApiModelProperty(value = "SKU中文名称")
    private String skuName;
    @ApiModelProperty(value = "入库数量")
    private int entryNum;
    @ApiModelProperty(value = "入备品数")
    private int entryFrets;
    @ApiModelProperty(value = "批次")
    private String batch;
    @ApiModelProperty(value = "不含税单价")
    private double unitPriceWithoutTax;
    @ApiModelProperty(value = "不含税金额")
    private double amountWithoutTax;
    @ApiModelProperty(value = "税率(%)")
    private double taxRate;
    @ApiModelProperty(value = "含税单价")
    private double unitPriceWithTax;
    @ApiModelProperty(value = "含税金额")
    private double amountWithTax;
    @ApiModelProperty(value = "是否需要包材")
    private String isNeedPacking;
    @ApiModelProperty(value = "包材明细")
    private List<ProductMachineInfoEntity> productMachineInfoEntityList;

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getEntryUser() {
        return entryUser;
    }

    public void setEntryUser(String entryUser) {
        this.entryUser = entryUser;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getSupplierCd() {
        return supplierCd;
    }

    public void setSupplierCd(String supplierCd) {
        this.supplierCd = supplierCd;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierDeliveryNum() {
        return supplierDeliveryNum;
    }

    public void setSupplierDeliveryNum(String supplierDeliveryNum) {
        this.supplierDeliveryNum = supplierDeliveryNum;
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

    public String getPackWarehouseId() {
        return packWarehouseId;
    }

    public void setPackWarehouseId(String packWarehouseId) {
        this.packWarehouseId = packWarehouseId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public int getEntryNum() {
        return entryNum;
    }

    public void setEntryNum(int entryNum) {
        this.entryNum = entryNum;
    }

    public int getEntryFrets() {
        return entryFrets;
    }

    public void setEntryFrets(int entryFrets) {
        this.entryFrets = entryFrets;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public double getUnitPriceWithoutTax() {
        return unitPriceWithoutTax;
    }

    public void setUnitPriceWithoutTax(double unitPriceWithoutTax) {
        this.unitPriceWithoutTax = unitPriceWithoutTax;
    }

    public double getAmountWithoutTax() {
        return amountWithoutTax;
    }

    public void setAmountWithoutTax(double amountWithoutTax) {
        this.amountWithoutTax = amountWithoutTax;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public double getUnitPriceWithTax() {
        return unitPriceWithTax;
    }

    public void setUnitPriceWithTax(double unitPriceWithTax) {
        this.unitPriceWithTax = unitPriceWithTax;
    }

    public double getAmountWithTax() {
        return amountWithTax;
    }

    public void setAmountWithTax(double amountWithTax) {
        this.amountWithTax = amountWithTax;
    }

    public String getIsNeedPacking() {
        return isNeedPacking;
    }

    public void setIsNeedPacking(String isNeedPacking) {
        this.isNeedPacking = isNeedPacking;
    }

    public List<ProductMachineInfoEntity> getProductMachineInfoEntityList() {
        return productMachineInfoEntityList;
    }

    public void setProductMachineInfoEntityList(List<ProductMachineInfoEntity> productMachineInfoEntityList) {
        this.productMachineInfoEntityList = productMachineInfoEntityList;
    }

    public String getPackWarehouseName() {
        return packWarehouseName;
    }

    public void setPackWarehouseName(String packWarehouseName) {
        this.packWarehouseName = packWarehouseName;
    }
}
