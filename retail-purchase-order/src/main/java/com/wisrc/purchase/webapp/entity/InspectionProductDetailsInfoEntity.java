package com.wisrc.purchase.webapp.entity;

import java.util.Objects;

public class InspectionProductDetailsInfoEntity {
    private String inspectionProductId;
    private String inspectionId;
    private String skuId;
    private Integer inspectedUndelivery;
    private Integer finishQuantity;
    private Integer deliveryQuantity;
    //    private Double spareRate;
    private Integer deliverySpareQuantity;
    private Double freight;
    private Integer inspectionQuantity;
    private Integer allowedQuantity;
    private Integer rejectionQuantity;
    private Integer receiptQuantity;
    private Integer receiptSpareQuantity;
    private Integer putInStorageQuantity;
    private Double volume;
    //    private Double grossWeight;
    private Integer statusCd;

    public String getInspectionProductId() {
        return inspectionProductId;
    }

    public void setInspectionProductId(String inspectionProductId) {
        this.inspectionProductId = inspectionProductId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Integer getInspectedUndelivery() {
        return inspectedUndelivery;
    }

    public void setInspectedUndelivery(Integer inspectedUndelivery) {
        this.inspectedUndelivery = inspectedUndelivery;
    }

    public Integer getFinishQuantity() {
        return finishQuantity;
    }

    public void setFinishQuantity(Integer finishQuantity) {
        this.finishQuantity = finishQuantity;
    }

    public Integer getDeliveryQuantity() {
        return deliveryQuantity;
    }

    public void setDeliveryQuantity(Integer deliveryQuantity) {
        this.deliveryQuantity = deliveryQuantity;
    }

//    public Double getSpareRate() {
//        return spareRate;
//    }

//    public void setSpareRate(Double spareRate) {
//        this.spareRate = spareRate;
//    }

    public Integer getDeliverySpareQuantity() {
        return deliverySpareQuantity;
    }

    public void setDeliverySpareQuantity(Integer deliverySpareQuantity) {
        this.deliverySpareQuantity = deliverySpareQuantity;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Integer getInspectionQuantity() {
        return inspectionQuantity;
    }

    public void setInspectionQuantity(Integer inspectionQuantity) {
        this.inspectionQuantity = inspectionQuantity;
    }

    public Integer getAllowedQuantity() {
        return allowedQuantity;
    }

    public void setAllowedQuantity(Integer allowedQuantity) {
        this.allowedQuantity = allowedQuantity;
    }

    public Integer getRejectionQuantity() {
        return rejectionQuantity;
    }

    public void setRejectionQuantity(Integer rejectionQuantity) {
        this.rejectionQuantity = rejectionQuantity;
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

    public Integer getPutInStorageQuantity() {
        return putInStorageQuantity;
    }

    public void setPutInStorageQuantity(Integer putInStorageQuantity) {
        this.putInStorageQuantity = putInStorageQuantity;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

//    public Double getGrossWeight() {
//        return grossWeight;
//    }

//    public void setGrossWeight(Double grossWeight) {
//        this.grossWeight = grossWeight;
//    }

    public String getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InspectionProductDetailsInfoEntity that = (InspectionProductDetailsInfoEntity) o;
        return Objects.equals(inspectionProductId, that.inspectionProductId) &&
                Objects.equals(inspectionId, that.inspectionId) &&
                Objects.equals(skuId, that.skuId) &&
                Objects.equals(inspectedUndelivery, that.inspectedUndelivery) &&
                Objects.equals(finishQuantity, that.finishQuantity) &&
                Objects.equals(deliveryQuantity, that.deliveryQuantity) &&
//                Objects.equals(spareRate, that.spareRate) &&
                Objects.equals(deliverySpareQuantity, that.deliverySpareQuantity) &&
                Objects.equals(freight, that.freight) &&
                Objects.equals(inspectionQuantity, that.inspectionQuantity) &&
                Objects.equals(allowedQuantity, that.allowedQuantity) &&
                Objects.equals(rejectionQuantity, that.rejectionQuantity) &&
                Objects.equals(receiptQuantity, that.receiptQuantity) &&
                Objects.equals(receiptSpareQuantity, that.receiptSpareQuantity) &&
                Objects.equals(putInStorageQuantity, that.putInStorageQuantity) &&
                Objects.equals(volume, that.volume) &&
//                Objects.equals(grossWeight, that.grossWeight) &&
                Objects.equals(statusCd, that.statusCd);
    }

//    @Override
//    public int hashCode() {
//
//        return Objects.hash(inspectionProductId, inspectionId, skuId, inspectedUndelivery, finishQuantity, deliveryQuantity, spareRate, deliverySpareQuantity, freight, inspectionQuantity, allowedQuantity, rejectionQuantity, receiptQuantity, receiptSpareQuantity, putInStorageQuantity, volume, grossWeight, statusCd);
//    }
}
