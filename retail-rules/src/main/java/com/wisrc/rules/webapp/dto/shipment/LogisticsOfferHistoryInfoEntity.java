package com.wisrc.rules.webapp.dto.shipment;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

public class LogisticsOfferHistoryInfoEntity {
    @ApiModelProperty(value = "主键ID", hidden = true)
    private String uuid;
    @ApiModelProperty(value = "报价ID", hidden = true)
    private String offerId;
    @ApiModelProperty(value = "更新时间", hidden = true)
    private String modifyTime;
    @ApiModelProperty(value = "更新人", hidden = true)
    private String modifyUser;
    @ApiModelProperty(value = "开始值", required = true)
    @Min(value = 0, message = "开始区间只能为非负数")
    private double startChargeSection;
    @ApiModelProperty(value = "结束值", required = true)
    @Min(value = 0, message = "结束区间只能为非负数")
    private double endChargeSection;
    @Min(value = 0, message = "单价只能为非负数")
    @ApiModelProperty(value = "单价", required = true)
    private double unitPrice;
    @Min(value = 0, message = "含油价只能为非负数")
    @ApiModelProperty(value = "含油价", required = true)
    private double unitPriceWithOil;
    @ApiModelProperty(value = "价格状态 0--正常，1--已删除")
    private int deleteStatus;

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

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
