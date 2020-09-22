package com.wisrc.replenishment.webapp.vo.wms;

public class TransferOutPackVO {

    private Double outerBoxSpecificationLen;

    private Double outerBoxSpecificationWidth;

    private Double outerBoxSpecificationHeight;

    private int packingQuantity;

    private Double packingWeight;

    private int numberOfBox;

    private int deliveryNumber;

    public Double getOuterBoxSpecificationLen() {
        return outerBoxSpecificationLen;
    }

    public void setOuterBoxSpecificationLen(Double outerBoxSpecificationLen) {
        this.outerBoxSpecificationLen = outerBoxSpecificationLen;
    }

    public Double getOuterBoxSpecificationWidth() {
        return outerBoxSpecificationWidth;
    }

    public void setOuterBoxSpecificationWidth(Double outerBoxSpecificationWidth) {
        this.outerBoxSpecificationWidth = outerBoxSpecificationWidth;
    }

    public Double getOuterBoxSpecificationHeight() {
        return outerBoxSpecificationHeight;
    }

    public void setOuterBoxSpecificationHeight(Double outerBoxSpecificationHeight) {
        this.outerBoxSpecificationHeight = outerBoxSpecificationHeight;
    }

    public int getPackingQuantity() {
        return packingQuantity;
    }

    public void setPackingQuantity(int packingQuantity) {
        this.packingQuantity = packingQuantity;
    }

    public Double getPackingWeight() {
        return packingWeight;
    }

    public void setPackingWeight(Double packingWeight) {
        this.packingWeight = packingWeight;
    }

    public int getNumberOfBox() {
        return numberOfBox;
    }

    public void setNumberOfBox(int numberOfBox) {
        this.numberOfBox = numberOfBox;
    }

    public int getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(int deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }
}
