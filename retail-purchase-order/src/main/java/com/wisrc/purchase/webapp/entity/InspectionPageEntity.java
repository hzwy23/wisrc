package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;

@ApiModel
public class InspectionPageEntity {

    private String arrivalProductId;
    @ApiModelProperty(value = "到货通知单号")
    private String arrivalId;
    @ApiModelProperty(value = "日期")
    private Date applyDate;
    @ApiModelProperty(value = "发起人")
    private String employeeId;
    @ApiModelProperty(value = "预计到货时间")
    private Date estimateArrivalDate;
    @ApiModelProperty(value = "供应商编号")
    private String supplierId;
    @ApiModelProperty(value = "库存SKU")
    private String skuId;
    @ApiModelProperty(value = "采购订单号")
    private String purchaseOrderId;
    @ApiModelProperty("物流单号")
    private String logisticsId;

    @ApiModelProperty(value = "提货数量")
    private Integer deliveryQuantity;

    @ApiModelProperty(value = "收获数量")
    private Integer receiptQuantity;

    @ApiModelProperty(value = "收备品数")
    private Integer receiptSpareQuantity;

    @ApiModelProperty(value = "状态")
    private Integer statusCd;

    public String getArrivalProductId() {
        return arrivalProductId;
    }

    public void setArrivalProductId(String arrivalProductId) {
        this.arrivalProductId = arrivalProductId;
    }

    public String getArrivalId() {
        return arrivalId;
    }

    public void setArrivalId(String arrivalId) {
        this.arrivalId = arrivalId;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Date getEstimateArrivalDate() {
        return estimateArrivalDate;
    }

    public void setEstimateArrivalDate(Date estimateArrivalDate) {
        this.estimateArrivalDate = estimateArrivalDate;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public Integer getDeliveryQuantity() {
        return deliveryQuantity;
    }

    public void setDeliveryQuantity(Integer deliveryQuantity) {
        this.deliveryQuantity = deliveryQuantity;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    public Integer getReceiptQuantity() {
        return receiptQuantity == null ? 0 : receiptQuantity;
    }

    public void setReceiptQuantity(Integer receiptQuantity) {
        this.receiptQuantity = receiptQuantity;
    }

    public Integer getReceiptSpareQuantity() {
        return receiptSpareQuantity == null ? 0 : receiptSpareQuantity;
    }

    public void setReceiptSpareQuantity(Integer receiptSpareQuantity) {
        this.receiptSpareQuantity = receiptSpareQuantity;
    }

    @Override
    public String toString() {
        return "InspectionPageEntity{" +
                "arrivalProductId='" + arrivalProductId + '\'' +
                ", arrivalId='" + arrivalId + '\'' +
                ", applyDate=" + applyDate +
                ", employeeId='" + employeeId + '\'' +
                ", estimateArrivalDate=" + estimateArrivalDate +
                ", supplierId='" + supplierId + '\'' +
                ", skuId='" + skuId + '\'' +
                ", purchaseOrderId='" + purchaseOrderId + '\'' +
                ", logisticsId='" + logisticsId + '\'' +
                ", deliveryQuantity=" + deliveryQuantity +
                ", statusCd=" + statusCd +
                '}';
    }
}
