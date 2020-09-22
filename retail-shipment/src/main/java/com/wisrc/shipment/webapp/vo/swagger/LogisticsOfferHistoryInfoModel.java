package com.wisrc.shipment.webapp.vo.swagger;

import io.swagger.annotations.ApiModelProperty;

public class LogisticsOfferHistoryInfoModel {
    @ApiModelProperty(value = "返回状态吗", position = 1)
    private int code;
    @ApiModelProperty(value = "返回消息", position = 2)
    private int msg;
    @ApiModelProperty(value = "主键ID")
    private String uuid;
    @ApiModelProperty(value = "报价ID")
    private String offerId;
    @ApiModelProperty(value = "更新时间")
    private String modifyTime;
    @ApiModelProperty(value = "更新人")
    private String modifyUser;
    @ApiModelProperty(value = "开始值")
    private double startChargeSection;
    @ApiModelProperty(value = "结束值")
    private double endChargeSection;
    @ApiModelProperty(value = "单价")
    private double unitPrice;
    @ApiModelProperty(value = "含油价")
    private double unitPriceWithOil;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public double getStartChargeSection() {
        return startChargeSection;
    }

    public void setStartChargeSection(double startChargeSection) {
        this.startChargeSection = startChargeSection;
    }

    public double getEndChargeSection() {
        return endChargeSection;
    }

    public void setEndChargeSection(double endChargeSection) {
        this.endChargeSection = endChargeSection;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getUnitPriceWithOil() {
        return unitPriceWithOil;
    }

    public void setUnitPriceWithOil(double unitPriceWithOil) {
        this.unitPriceWithOil = unitPriceWithOil;
    }
}
