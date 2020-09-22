package com.wisrc.purchase.webapp.vo;

import com.wisrc.purchase.webapp.utils.DateUtil;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class OrderTracingCompleteVo {
    @ApiModelProperty(value = "完工数", required = false)
    private Integer completeNum;
    @ApiModelProperty(value = "提货数", required = false)
    private Integer pickNum;
    @ApiModelProperty(value = "单据类型", required = false)
    private String billType;
    @ApiModelProperty(value = "单据编号", required = false)
    private String billId;
    @ApiModelProperty(value = "日期", required = false)
    private String date;

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
        Date date = DateUtil.convertStrToDate(this.date, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
        return DateUtil.convertDateToStr(date, DateUtil.DEFAULT_SHORT_DATE_FORMAT);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCompleteNum() {
        return completeNum;
    }

    public void setCompleteNum(Integer completeNum) {
        this.completeNum = completeNum;
    }

    public Integer getPickNum() {
        return pickNum;
    }

    public void setPickNum(Integer pickNum) {
        this.pickNum = pickNum;
    }
}
