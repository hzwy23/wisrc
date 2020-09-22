package com.wisrc.replenishment.webapp.vo.delivery;

import java.sql.Date;

/**
 * 计算油价和金额后的值
 */
public class CalcuDeliveryVO {

    private String offerId;//物流商id
    private String unitPriceWithOil;//含油价 预估单价
    private String chargeIntever;//计费区间
    private String totalAmount;//总金额

    private Double customsDeclarationFee;//报关费
    private Double portFee;//过港费
    private String totalWeight;//预估计费重
    private String freightTotal;//预估运费
    private String annexCost;//附加费
    private String totalCost;//费用合计
    private String weighTypeCd;//类型。实重、抛重
    private Date estimateDate;//预计签收日期

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getUnitPriceWithOil() {
        return unitPriceWithOil;
    }

    public void setUnitPriceWithOil(String unitPriceWithOil) {
        this.unitPriceWithOil = unitPriceWithOil;
    }

    public String getChargeIntever() {
        return chargeIntever;
    }

    public void setChargeIntever(String chargeIntever) {
        this.chargeIntever = chargeIntever;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getFreightTotal() {
        return freightTotal;
    }

    public void setFreightTotal(String freightTotal) {
        this.freightTotal = freightTotal;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getAnnexCost() {
        return annexCost;
    }

    public void setAnnexCost(String annexCost) {
        this.annexCost = annexCost;
    }

    public String getWeighTypeCd() {
        return weighTypeCd;
    }

    public void setWeighTypeCd(String weighTypeCd) {
        this.weighTypeCd = weighTypeCd;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public Double getCustomsDeclarationFee() {
        return customsDeclarationFee;
    }

    public void setCustomsDeclarationFee(Double customsDeclarationFee) {
        this.customsDeclarationFee = customsDeclarationFee;
    }

    public Double getPortFee() {
        return portFee;
    }

    public void setPortFee(Double portFee) {
        this.portFee = portFee;
    }

    @Override
    public String toString() {
        return "CalcuDeliveryVO{" +
                "offerId='" + offerId + '\'' +
                ", unitPriceWithOil='" + unitPriceWithOil + '\'' +
                ", chargeIntever='" + chargeIntever + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", customsDeclarationFee ='" + customsDeclarationFee + '\'' +
                ", portFee='" + portFee + '\'' +
                ", totalWeight='" + totalWeight + '\'' +
                ", freightTotal='" + freightTotal + '\'' +
                ", annexCost='" + annexCost + '\'' +
                ", totalCost='" + totalCost + '\'' +
                ", weighTypeCd='" + weighTypeCd + '\'' +
                ", estimateDate=" + estimateDate +
                '}';
    }
}
