package com.wisrc.purchase.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class OrderTracingEntryVo {
    @ApiModelProperty(value = "入库数", required = false)
    private Integer entryNum;
    @ApiModelProperty(value = "收货数", required = false)
    private Integer receiptQuantity;
    @ApiModelProperty(value = "拒收数", required = false)
    private Integer rejectQuantity;
    @ApiModelProperty(value = "单据类型", required = false)
    private String billType;
    @ApiModelProperty(value = "单据编号", required = false)
    private String billId;
    @ApiModelProperty(value = "日期", required = false)
    private String date;

    public Integer getEntryNum() {
        return entryNum;
    }

    public void setEntryNum(Integer entryNum) {
        this.entryNum = entryNum;
    }

    public Integer getReceiptQuantity() {
        return receiptQuantity;
    }

    public void setReceiptQuantity(Integer receiptQuantity) {
        this.receiptQuantity = receiptQuantity;
    }

    public Integer getRejectQuantity() {
        return rejectQuantity;
    }

    public void setRejectQuantity(Integer rejectQuantity) {
        this.rejectQuantity = rejectQuantity;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
