package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class LogisticsBillEnity {
    @ApiModelProperty(value = "运单号", required = true)
    @NotEmpty
    private String waybillId;
    @ApiModelProperty(value = "卖家公司名称", required = true)
    @NotEmpty
    private String sellCompanyName;
    @ApiModelProperty(value = "联系人", required = true)
    @NotEmpty
    private String sellContact;
    @ApiModelProperty(value = "发货地址", required = true)
    @NotEmpty
    private String sendAddress;
    @ApiModelProperty(value = "买家公司名称", required = true)
    @NotEmpty
    private String buyCompanyName;
    @ApiModelProperty(value = "VTA税号", required = true)
    @NotEmpty
    private String vatNo;
    @ApiModelProperty(value = "收货地址", required = true)
    @NotEmpty
    private String receiveAddress;
    @ApiModelProperty(value = "发票备注", required = false)
    private String invoiceRemark;
    @ApiModelProperty(value = "申报种类数量", required = false, hidden = true)
    private int declareKindCnt;
    @ApiModelProperty(value = "申报数量", required = false, hidden = true)
    private double declareQuantity;
    @ApiModelProperty(value = "申报总金额", required = false, hidden = true)
    private double declareAmount;
    @ApiModelProperty(value = "状态，0--正常，1--已删除", required = false)
    private int deleteStatus;
    @ApiModelProperty(value = "创建人", required = false, hidden = true)
    private String userId;
    @NotEmpty
    @Valid
    private List<ProductDetailsEnity> productDetailList;

    public double getDeclareAmount() {
        return declareAmount;
    }

    public void setDeclareAmount(double declareAmount) {
        this.declareAmount = declareAmount;
    }

    public String getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(String waybillId) {
        this.waybillId = waybillId;
    }


    public String getSellCompanyName() {
        return sellCompanyName;
    }

    public void setSellCompanyName(String sellCompanyName) {
        this.sellCompanyName = sellCompanyName;
    }

    public String getSellContact() {
        return sellContact;
    }

    public void setSellContact(String sellContact) {
        this.sellContact = sellContact;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getBuyCompanyName() {
        return buyCompanyName;
    }

    public void setBuyCompanyName(String buyCompanyName) {
        this.buyCompanyName = buyCompanyName;
    }

    public String getVatNo() {
        return vatNo;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getInvoiceRemark() {
        return invoiceRemark;
    }

    public void setInvoiceRemark(String invoiceRemark) {
        this.invoiceRemark = invoiceRemark;
    }

    public int getDeclareKindCnt() {
        return declareKindCnt;
    }

    public void setDeclareKindCnt(int declareKindCnt) {
        this.declareKindCnt = declareKindCnt;
    }


    public double getDeclareQuantity() {
        return declareQuantity;
    }

    public void setDeclareQuantity(double declareQuantity) {
        this.declareQuantity = declareQuantity;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public List<ProductDetailsEnity> getProductDetailList() {
        return productDetailList;
    }

    public void setProductDetailList(List<ProductDetailsEnity> productDetailList) {
        this.productDetailList = productDetailList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
