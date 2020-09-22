package com.wisrc.purchase.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class OrderTracingReturnVo {
    @ApiModelProperty(value = "退货数", required = false)
    private Integer rejectQuantity;
    @ApiModelProperty(value = "单据类型", required = false)
    private String billType;
    @ApiModelProperty(value = "单据编号", required = false)
    private String billId;
    @ApiModelProperty(value = "日期", required = false)
    private String date;

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
