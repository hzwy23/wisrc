package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

@Data
public class ArrivalProductDetailsInfoEntity {
    @ApiModelProperty(value = "到货产品ID")
    private String arrivalProductId;
    @ApiModelProperty(value = "到货通知单ID")
    private String arrivalId;
    @ApiModelProperty(value = "库存SKU")
    private String skuId;
    @ApiModelProperty(value = "入库欠数")
    private Integer warehouseOweNum;
    @ApiModelProperty(value = "备品率")
    private BigDecimal spareRate;
    @ApiModelProperty(value = "提货数量")
    private Integer deliveryQuantity;
    @ApiModelProperty(value = "提备品数")
    private Integer deliverySpareQuantity;
    @ApiModelProperty(value = "运费")
    private BigDecimal freight;
    @ApiModelProperty(value = "验货数量")
    private Integer inspectionQuantity;
    @ApiModelProperty(value = "合格数")
    private Integer qualifiedQualified;
    @ApiModelProperty(value = "不合格数")
    private Integer unqualifiedQuantity;
    @ApiModelProperty(value = "收货数量")
    private Integer receiptQuantity;
    @ApiModelProperty(value = "收备品数")
    private Integer receiptSpareQuantity;
    @ApiModelProperty(value = "箱数")
    private Integer cartonNum;
    @ApiModelProperty(value = "尾数")
    private Integer mantissaNum;
    @ApiModelProperty(value = "装箱率")
    private BigDecimal packingRate;
    @ApiModelProperty(value = "体积")
    private BigDecimal volume;
    @ApiModelProperty(value = "毛重")
    private BigDecimal grossWeight;
    @ApiModelProperty(value = "完工数量")
    private Integer finishQuantity;
    @ApiModelProperty(value = "删除标识 0：正常，1：删除")
    private Integer deleteStatus;
    @ApiModelProperty(value = "到货单状态,默认为1")
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

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Integer getWarehouseOweNum() {
        return warehouseOweNum;
    }

    public void setWarehouseOweNum(Integer warehouseOweNum) {
        this.warehouseOweNum = warehouseOweNum;
    }

    public BigDecimal getSpareRate() {
        return spareRate;
    }

    public void setSpareRate(BigDecimal spareRate) {
        this.spareRate = spareRate;
    }

    public Integer getDeliveryQuantity() {
        return deliveryQuantity;
    }

    public void setDeliveryQuantity(Integer deliveryQuantity) {
        this.deliveryQuantity = deliveryQuantity;
    }

    public Integer getDeliverySpareQuantity() {
        return deliverySpareQuantity;
    }

    public void setDeliverySpareQuantity(Integer deliverySpareQuantity) {
        this.deliverySpareQuantity = deliverySpareQuantity;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public Integer getInspectionQuantity() {
        return inspectionQuantity;
    }

    public void setInspectionQuantity(Integer inspectionQuantity) {
        this.inspectionQuantity = inspectionQuantity;
    }

    public Integer getQualifiedQualified() {
        return qualifiedQualified;
    }

    public void setQualifiedQualified(Integer qualifiedQualified) {
        this.qualifiedQualified = qualifiedQualified;
    }

    public Integer getUnqualifiedQuantity() {
        return unqualifiedQuantity;
    }

    public void setUnqualifiedQuantity(Integer unqualifiedQuantity) {
        this.unqualifiedQuantity = unqualifiedQuantity;
    }

    public Integer getReceiptQuantity() {
        return receiptQuantity;
    }

    public void setReceiptQuantity(Integer receiptQuantity) {
        this.receiptQuantity = receiptQuantity;
    }

    public Integer getReceiptSpareQuantity() {
        return receiptSpareQuantity;
    }

    public void setReceiptSpareQuantity(Integer receiptSpareQuantity) {
        this.receiptSpareQuantity = receiptSpareQuantity;
    }

    public Integer getCartonNum() {
        return cartonNum;
    }

    public void setCartonNum(Integer cartonNum) {
        this.cartonNum = cartonNum;
    }

    public Integer getMantissaNum() {
        return mantissaNum;
    }

    public void setMantissaNum(Integer mantissaNum) {
        this.mantissaNum = mantissaNum;
    }

    public BigDecimal getPackingRate() {
        return packingRate;
    }

    public void setPackingRate(BigDecimal packingRate) {
        this.packingRate = packingRate;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    public Integer getFinishQuantity() {
        return finishQuantity;
    }

    public void setFinishQuantity(Integer finishQuantity) {
        this.finishQuantity = finishQuantity;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrivalProductDetailsInfoEntity that = (ArrivalProductDetailsInfoEntity) o;
        return Objects.equals(arrivalProductId, that.arrivalProductId) &&
                Objects.equals(arrivalId, that.arrivalId) &&
                Objects.equals(skuId, that.skuId) &&
                Objects.equals(warehouseOweNum, that.warehouseOweNum) &&
                Objects.equals(spareRate, that.spareRate) &&
                Objects.equals(deliveryQuantity, that.deliveryQuantity) &&
                Objects.equals(deliverySpareQuantity, that.deliverySpareQuantity) &&
                Objects.equals(freight, that.freight) &&
                Objects.equals(inspectionQuantity, that.inspectionQuantity) &&
                Objects.equals(qualifiedQualified, that.qualifiedQualified) &&
                Objects.equals(unqualifiedQuantity, that.unqualifiedQuantity) &&
                Objects.equals(receiptQuantity, that.receiptQuantity) &&
                Objects.equals(receiptSpareQuantity, that.receiptSpareQuantity) &&
                Objects.equals(cartonNum, that.cartonNum) &&
                Objects.equals(mantissaNum, that.mantissaNum) &&
                Objects.equals(packingRate, that.packingRate) &&
                Objects.equals(volume, that.volume) &&
                Objects.equals(grossWeight, that.grossWeight) &&
                Objects.equals(finishQuantity, that.finishQuantity) &&
                Objects.equals(deleteStatus, that.deleteStatus);
    }

    @Override
    public int hashCode() {

        return Objects.hash(arrivalProductId, arrivalId, skuId, warehouseOweNum, spareRate, deliveryQuantity, deliverySpareQuantity, freight, inspectionQuantity, qualifiedQualified, unqualifiedQuantity, receiptQuantity, receiptSpareQuantity, cartonNum, mantissaNum, packingRate, volume, grossWeight, finishQuantity, deleteStatus);
    }
}
