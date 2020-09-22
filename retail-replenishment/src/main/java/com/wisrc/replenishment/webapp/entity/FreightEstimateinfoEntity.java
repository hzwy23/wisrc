package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;
import java.sql.Timestamp;

@Api(tags = "运费估算信息")
public class FreightEstimateinfoEntity {
    @ApiModelProperty(value = "运单号")
    private String waybillId;
    @ApiModelProperty(value = "称重方式")
    private int weighTypeCd;
    @ApiModelProperty(value = "报关类型")
    private int customsTypeCd;
    @ApiModelProperty(value = "预估签收日期")
    private Date estimateDate;
    @ApiModelProperty(value = "预估单价(元)")
    private double unitPrice;
    @ApiModelProperty(value = "预估计费重(kg)")
    private double totalWeight;
    @ApiModelProperty(value = "预估运费(元)")
    private double freightTotal;
    @ApiModelProperty(value = "附加费(元)")
    private double annexCost;
    @ApiModelProperty(value = "其他费用(元)")
    private double otherFee;
    @ApiModelProperty(value = "折扣金额(元)")
    private double discountedAmount;
    @ApiModelProperty(value = "费用合计(元)")
    private Double totalCost;
    @ApiModelProperty(value = "删除标示")
    private int deleteStatus;
    @ApiModelProperty(value = "创建人", hidden = true)
    private String createUser;
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Timestamp createTime;
    @ApiModelProperty(value = "修改人", hidden = true)
    private String modifyUser;
    @ApiModelProperty(value = "修改时间", hidden = true)
    private Timestamp modifyTime;

    public double getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(double otherFee) {
        this.otherFee = otherFee;
    }

    public String getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(String waybillId) {
        this.waybillId = waybillId;
    }

    public int getWeighTypeCd() {
        return weighTypeCd;
    }

    public void setWeighTypeCd(int weighTypeCd) {
        this.weighTypeCd = weighTypeCd;
    }

    public int getCustomsTypeCd() {
        return customsTypeCd;
    }

    public void setCustomsTypeCd(int customsTypeCd) {
        this.customsTypeCd = customsTypeCd;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public double getFreightTotal() {
        return freightTotal;
    }

    public void setFreightTotal(double freightTotal) {
        this.freightTotal = freightTotal;
    }

    public double getAnnexCost() {
        return annexCost;
    }

    public void setAnnexCost(double annexCost) {
        this.annexCost = annexCost;
    }

    public double getDiscountedAmount() {
        return discountedAmount;
    }

    public void setDiscountedAmount(double discountedAmount) {
        this.discountedAmount = discountedAmount;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "FreightEstimateinfoEntity{" +
                "waybillId='" + waybillId + '\'' +
                ", weighTypeCd=" + weighTypeCd +
                ", customsTypeCd=" + customsTypeCd +
                ", estimateDate=" + estimateDate +
                ", unitPrice=" + unitPrice +
                ", totalWeight=" + totalWeight +
                ", freightTotal=" + freightTotal +
                ", annexCost=" + annexCost +
                ", discountedAmount=" + discountedAmount +
                ", totalCost=" + totalCost +
                ", deleteStatus=" + deleteStatus +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
