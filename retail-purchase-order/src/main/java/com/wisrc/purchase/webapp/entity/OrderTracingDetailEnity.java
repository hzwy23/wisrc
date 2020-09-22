package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;
import java.util.List;

public class OrderTracingDetailEnity {
    @ApiModelProperty(value = "采购订单产品id）")
    private String id;
    @ApiModelProperty(value = "采购订单号）")
    private String orderId;
    @ApiModelProperty(value = "订单日期）", required = false)
    private Date billDate;
    @ApiModelProperty(value = "sku编号）", required = false)
    private String skuId;
    @ApiModelProperty(value = "产品中文名）", required = false)
    private String skuNameZh;
    @ApiModelProperty(value = "供应商ID）")
    private String supplierId;
    @ApiModelProperty(value = "供应商名称）")
    private String supplierName;
    @ApiModelProperty(value = "采购员）")
    private String employeeId;
    @ApiModelProperty(value = "采购员名称）")
    private String employeeName;
    @ApiModelProperty(value = "备注）")
    private String remark;
    @ApiModelProperty(value = "不含税金额）")
    private double amountWithoutTax;
    @ApiModelProperty(value = "含税金额）")
    private double amountWithTax;
    @ApiModelProperty(value = "不含税单价）")
    private double unitPriceWithoutTax;
    @ApiModelProperty(value = "含税单价）")
    private double unitPriceWithTax;
    @ApiModelProperty(value = "税率）")
    private double taxRate;
    @ApiModelProperty(value = "是否开票类型）")
    private Integer tiicketOpenCd;
    @ApiModelProperty(value = "报关状态）")
    private Integer customsTypeCd;
    @ApiModelProperty(value = "付款条款）")
    private String paymentProvision;
    @ApiModelProperty(value = "交货状态）")
    private int deliveryTypeCd;
    @ApiModelProperty(value = "运费）")
    private double freight;
    @ApiModelProperty(value = "金额合计）")
    private double totalAmount;
    @ApiModelProperty(value = "订单状态）")
    private int orderStatus;
    @ApiModelProperty(value = "订单数量")
    private int quantity;
    @ApiModelProperty(value = "订单备注")
    private String orderRemark;
    @ApiModelProperty(value = "完工数")
    private int finishQuantity;
    @ApiModelProperty(value = "提货数")
    private int deliveryQuantity;
    @ApiModelProperty(value = "库存数")
    private int spareQuantity;
    @ApiModelProperty(value = "收货数")
    private int receiptQuantity;
    @ApiModelProperty(value = "入库数")
    private int entryNum;
    @ApiModelProperty(value = "退货数")
    private int returnQuantity;
    @ApiModelProperty(value = "拒收数")
    private int rejectionQuantity;
    @ApiModelProperty(value = "尚欠数量")
    private double lackNum;
    @ApiModelProperty(value = "备品率")
    private double spareRate;
    @ApiModelProperty(value = "订单备品数")
    private Integer spareNum;
    @ApiModelProperty(value = "备品入库数")
    private double wareHouesEntryFrets;
    @ApiModelProperty(value = "备品退货数")
    private double returnEntryFrets;
    @ApiModelProperty(value = "欠备品数")
    private double lackEntryFrets;

    @ApiModelProperty(value = "交货日期|数量")
    private List<ProductDeliveryInfoEntity> deliveryDateAndTimeList;

    public List<ProductDeliveryInfoEntity> getDeliveryDateAndTimeList() {
        return deliveryDateAndTimeList;
    }

    public void setDeliveryDateAndTimeList(List<ProductDeliveryInfoEntity> deliveryDateAndTimeList) {
        this.deliveryDateAndTimeList = deliveryDateAndTimeList;
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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getUnitPriceWithoutTax() {
        return unitPriceWithoutTax;
    }

    public void setUnitPriceWithoutTax(double unitPriceWithoutTax) {
        this.unitPriceWithoutTax = unitPriceWithoutTax;
    }

    public double getUnitPriceWithTax() {
        return unitPriceWithTax;
    }

    public void setUnitPriceWithTax(double unitPriceWithTax) {
        this.unitPriceWithTax = unitPriceWithTax;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public int getTiicketOpenCd() {
        return tiicketOpenCd;
    }

    public void setTiicketOpenCd(int tiicketOpenCd) {
        this.tiicketOpenCd = tiicketOpenCd;
    }

    public void setTiicketOpenCd(Integer tiicketOpenCd) {
        this.tiicketOpenCd = tiicketOpenCd;
    }

    public int getCustomsTypeCd() {
        return customsTypeCd;
    }

    public void setCustomsTypeCd(int customsTypeCd) {
        this.customsTypeCd = customsTypeCd;
    }

    public void setCustomsTypeCd(Integer customsTypeCd) {
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

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getFinishQuantity() {
        return finishQuantity;
    }

    public void setFinishQuantity(int finishQuantity) {
        this.finishQuantity = finishQuantity;
    }

    public int getDeliveryQuantity() {
        return deliveryQuantity;
    }

    public void setDeliveryQuantity(int deliveryQuantity) {
        this.deliveryQuantity = deliveryQuantity;
    }

    public int getSpareQuantity() {
        return spareQuantity;
    }

    public void setSpareQuantity(int spareQuantity) {
        this.spareQuantity = spareQuantity;
    }

    public int getReceiptQuantity() {
        return receiptQuantity;
    }

    public void setReceiptQuantity(int receiptQuantity) {
        this.receiptQuantity = receiptQuantity;
    }

    public int getEntryNum() {
        return entryNum;
    }

    public void setEntryNum(int entryNum) {
        this.entryNum = entryNum;
    }

    public int getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(int returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public void setLackNum(int lackNum) {
        this.lackNum = lackNum;
    }

    public void setSpareRate(int spareRate) {
        this.spareRate = spareRate;
    }

    public void setSpareNum(int spareNum) {
        this.spareNum = spareNum;
    }

    public void setReturnEntryFrets(int returnEntryFrets) {
        this.returnEntryFrets = returnEntryFrets;
    }

    public double getLackEntryFrets() {
        return lackEntryFrets;
    }

    public void setLackEntryFrets(double lackEntryFrets) {
        this.lackEntryFrets = lackEntryFrets;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getSpareNum() {
        return spareNum;
    }

    public void setSpareNum(Integer spareNum) {
        this.spareNum = spareNum;
    }

    public void setWareHouesEntryFrets(int wareHouesEntryFrets) {
        this.wareHouesEntryFrets = wareHouesEntryFrets;
    }

    public String getSkuNameZh() {
        return skuNameZh;
    }

    public void setSkuNameZh(String skuNameZh) {
        this.skuNameZh = skuNameZh;
    }

    public double getLackNum() {
        return lackNum;
    }

    public void setLackNum(double lackNum) {
        this.lackNum = lackNum;
    }

    public double getSpareRate() {
        return spareRate;
    }

    public void setSpareRate(double spareRate) {
        this.spareRate = spareRate;
    }

    public double getWareHouesEntryFrets() {
        return wareHouesEntryFrets;
    }

    public void setWareHouesEntryFrets(double wareHouesEntryFrets) {
        this.wareHouesEntryFrets = wareHouesEntryFrets;
    }

    public double getReturnEntryFrets() {
        return returnEntryFrets;
    }

    public void setReturnEntryFrets(double returnEntryFrets) {
        this.returnEntryFrets = returnEntryFrets;
    }

    public int getRejectionQuantity() {
        return rejectionQuantity;
    }

    public void setRejectionQuantity(int rejectionQuantity) {
        this.rejectionQuantity = rejectionQuantity;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }
}
