package com.wisrc.order.webapp.entity;


import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderBasicInfoEntity {
    @ApiModelProperty(value = "订单Id", required = false, hidden = true)
    private String orderId;
    @ApiModelProperty(value = "原始订单号", required = false)
    private String originalOrderId;
    @ApiModelProperty(value = "交易号", required = false)
    private String tradeNumber;
    @ApiModelProperty(value = "订单状态", required = false, hidden = true)
    private int statusCd;
    @ApiModelProperty(value = "平台Id", required = true)
    @NotEmpty(message = "平台Id不能为空")
    private String platId;
    @ApiModelProperty(value = "店铺Id", required = true)
    @NotEmpty(message = "店铺Id不能为空")
    private String shopId;
    @ApiModelProperty(value = "支付状态Id", required = true)
    @NotNull(message = "支付状态不能为空")
    private Integer payStatusCd;
    @ApiModelProperty(value = "支付方式Id", required = true)
    @NotNull(message = "支付方式不能为空")
    private Integer payTypeCd;
    @ApiModelProperty(value = "原始币种Id", required = true)
    @NotEmpty(message = "原始币种不能为空")
    private String originalCurrencyCd;
    @ApiModelProperty(value = "金额", required = true)
    @NotNull(message = "金额不能为空")
    private Double amountMoney;
    private String amountMoneyCurrency;
    @ApiModelProperty(value = "运费", required = true)
    @NotNull(message = "运费不能为空")
    private Double freight;
    @ApiModelProperty(value = "运费币种", required = true)
    @NotEmpty(message = "运费币种不能为空")
    private String freightCurrency;
    @ApiModelProperty(value = "退货金额", required = false)
    private Double returnAmount;
    @ApiModelProperty(value = "退货金额币种", required = true)
    @NotEmpty(message = "退货金额币种不能为空")
    private String returnAmountCurrency;
    @ApiModelProperty(value = "保险金额", required = false)
    private Double insuranceAmount;
    @ApiModelProperty(value = "保险金额币种", required = true)
    @NotEmpty(message = "保险金额币种不能为空")
    private String insuranceAmountCurrency;
    @ApiModelProperty(value = "平台费用", required = false)
    private Double platformFreight;
    @ApiModelProperty(value = "平台费用币种", required = true)
    @NotEmpty(message = "平台费用币种不能为空")
    private String platformFreightCurrency;
    @ApiModelProperty(value = "转账费用", required = false)
    private Double transferExpense;
    @ApiModelProperty(value = "转账费用币种", required = true)
    @NotEmpty(message = "转账费用币种不能为空")
    private String transferExpenseCurrency;
    @ApiModelProperty(value = "其它费用", required = false)
    private Double otherExpenses;
    @ApiModelProperty(value = "其它费用币种", required = true)
    @NotEmpty(message = "其它费用币种不能为空")
    private String otherExpensesCurrency;
    @ApiModelProperty(value = "收款账号", required = false)
    private String receiptAccount;
    @ApiModelProperty(value = "付款时间", required = false)
    private String paymentDate;
    @ApiModelProperty(value = "手工创建标识", required = false, hidden = true)
    private int manualCreation;
    @ApiModelProperty(value = "创建人", required = false, hidden = true)
    private String createUser;
    @ApiModelProperty(value = "创建时间", required = false, hidden = true)
    private String createTime;
    @ApiModelProperty(value = "更新人", required = false, hidden = true)
    private String modifyUser;
    @ApiModelProperty(value = "更新时间", required = false, hidden = true)
    private String modifyTime;
    private int deleteStatus;
    @Valid
    private OrderCustomerInfoEntity customsInfo;
    @ApiModelProperty(value = "物流信息", required = false, hidden = false)
    private OrderLogisticsInfo shipMentInfo;

    @ApiModelProperty(value = "商品明细信息", required = true, hidden = false)
    @NotEmpty(message = "商品明细不能为空")
    @Valid
    private List<OrderCommodityInfoEntity> productDetailList;
    @ApiModelProperty(value = "备注信息列表", required = false, hidden = true)
    private List<OrderRemarkInfoEntity> orderRemarkInfoEntityList;
    @ApiModelProperty(value = "修改记录列表", required = false, hidden = true)
    private List<UpdateDetailEnity> updateDetailEnityList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOriginalOrderId() {
        return originalOrderId;
    }

    public void setOriginalOrderId(String originalOrderId) {
        this.originalOrderId = originalOrderId;
    }

    public String getTradeNumber() {
        return tradeNumber;
    }

    public void setTradeNumber(String tradeNumber) {
        this.tradeNumber = tradeNumber;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getPlatId() {
        return platId;
    }

    public void setPlatId(String platId) {
        this.platId = platId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public int getPayStatusCd() {
        return payStatusCd;
    }

    public void setPayStatusCd(Integer payStatusCd) {
        this.payStatusCd = payStatusCd;
    }

    public void setPayStatusCd(int payStatusCd) {
        this.payStatusCd = payStatusCd;
    }

    public Integer getPayTypeCd() {
        return payTypeCd;
    }

    public void setPayTypeCd(Integer payTypeCd) {
        this.payTypeCd = payTypeCd;
    }

    public String getOriginalCurrencyCd() {
        return originalCurrencyCd;
    }

    public void setOriginalCurrencyCd(String originalCurrencyCd) {
        this.originalCurrencyCd = originalCurrencyCd;
    }

    public Double getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(Double amountMoney) {
        this.amountMoney = amountMoney;
    }

    public String getAmountMoneyCurrency() {
        return amountMoneyCurrency;
    }

    public void setAmountMoneyCurrency(String amountMoneyCurrency) {
        this.amountMoneyCurrency = amountMoneyCurrency;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public String getFreightCurrency() {
        return freightCurrency;
    }

    public void setFreightCurrency(String freightCurrency) {
        this.freightCurrency = freightCurrency;
    }

    public Double getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Double returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getReturnAmountCurrency() {
        return returnAmountCurrency;
    }

    public void setReturnAmountCurrency(String returnAmountCurrency) {
        this.returnAmountCurrency = returnAmountCurrency;
    }

    public Double getInsuranceAmount() {
        return insuranceAmount;
    }

    public void setInsuranceAmount(Double insuranceAmount) {
        this.insuranceAmount = insuranceAmount;
    }

    public String getInsuranceAmountCurrency() {
        return insuranceAmountCurrency;
    }

    public void setInsuranceAmountCurrency(String insuranceAmountCurrency) {
        this.insuranceAmountCurrency = insuranceAmountCurrency;
    }

    public Double getPlatformFreight() {
        return platformFreight;
    }

    public void setPlatformFreight(Double platformFreight) {
        this.platformFreight = platformFreight;
    }

    public String getPlatformFreightCurrency() {
        return platformFreightCurrency;
    }

    public void setPlatformFreightCurrency(String platformFreightCurrency) {
        this.platformFreightCurrency = platformFreightCurrency;
    }

    public Double getTransferExpense() {
        return transferExpense;
    }

    public void setTransferExpense(Double transferExpense) {
        this.transferExpense = transferExpense;
    }

    public String getTransferExpenseCurrency() {
        return transferExpenseCurrency;
    }

    public void setTransferExpenseCurrency(String transferExpenseCurrency) {
        this.transferExpenseCurrency = transferExpenseCurrency;
    }

    public Double getOtherExpenses() {
        return otherExpenses;
    }

    public void setOtherExpenses(Double otherExpenses) {
        this.otherExpenses = otherExpenses;
    }

    public String getOtherExpensesCurrency() {
        return otherExpensesCurrency;
    }

    public void setOtherExpensesCurrency(String otherExpensesCurrency) {
        this.otherExpensesCurrency = otherExpensesCurrency;
    }

    public String getReceiptAccount() {
        return receiptAccount;
    }

    public void setReceiptAccount(String receiptAccount) {
        this.receiptAccount = receiptAccount;
    }

    public int getManualCreation() {
        return manualCreation;
    }

    public void setManualCreation(int manualCreation) {
        this.manualCreation = manualCreation;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public OrderCustomerInfoEntity getCustomsInfo() {
        return customsInfo;
    }

    public void setCustomsInfo(OrderCustomerInfoEntity customsInfo) {
        this.customsInfo = customsInfo;
    }

    public OrderLogisticsInfo getShipMentInfo() {
        return shipMentInfo;
    }

    public void setShipMentInfo(OrderLogisticsInfo shipMentInfo) {
        this.shipMentInfo = shipMentInfo;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public List<OrderCommodityInfoEntity> getProductDetailList() {
        return productDetailList;
    }

    public void setProductDetailList(List<OrderCommodityInfoEntity> productDetailList) {
        this.productDetailList = productDetailList;
    }

    public List<OrderRemarkInfoEntity> getOrderRemarkInfoEntityList() {
        return orderRemarkInfoEntityList;
    }

    public void setOrderRemarkInfoEntityList(List<OrderRemarkInfoEntity> orderRemarkInfoEntityList) {
        this.orderRemarkInfoEntityList = orderRemarkInfoEntityList;
    }

    public List<UpdateDetailEnity> getUpdateDetailEnityList() {
        return updateDetailEnityList;
    }

    public void setUpdateDetailEnityList(List<UpdateDetailEnity> updateDetailEnityList) {
        this.updateDetailEnityList = updateDetailEnityList;
    }

    @Override
    public String toString() {
        return "OrderBasicInfo{" +
                "orderId='" + orderId + '\'' +
                ", originalOrderId='" + originalOrderId + '\'' +
                ", tradeNumber='" + tradeNumber + '\'' +
                ", statusCd=" + statusCd +
                ", platId='" + platId + '\'' +
                ", shopId='" + shopId + '\'' +
                ", payStatusCd=" + payStatusCd +
                ", payTypeCd=" + payTypeCd +
                ", originalCurrencyCd='" + originalCurrencyCd + '\'' +
                ", amountMoney=" + amountMoney +
                ", amountMoneyCurrency='" + amountMoneyCurrency + '\'' +
                ", freight=" + freight +
                ", freightCurrency='" + freightCurrency + '\'' +
                ", returnAmount=" + returnAmount +
                ", returnAmountCurrency='" + returnAmountCurrency + '\'' +
                ", insuranceAmount=" + insuranceAmount +
                ", insuranceAmountCurrency='" + insuranceAmountCurrency + '\'' +
                ", platformFreight=" + platformFreight +
                ", platformFreightCurrency='" + platformFreightCurrency + '\'' +
                ", transferExpense=" + transferExpense +
                ", transferExpenseCurrency='" + transferExpenseCurrency + '\'' +
                ", otherExpenses=" + otherExpenses +
                ", otherExpensesCurrency='" + otherExpensesCurrency + '\'' +
                ", receiptAccount='" + receiptAccount + '\'' +
                ", paymentDate=" + paymentDate +
                ", manualCreation=" + manualCreation +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyTime=" + modifyTime +
                ", deleteStatus=" + deleteStatus +
                '}';
    }
}
