package com.wisrc.shipment.webapp.vo;

public class LogisticHistoryChargeVO {
    private String uuid;
    private String offerId;
    private String modifyTime;
    private String section;
    private String unitPrice;
    private String unitPriceWithOil;

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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitPriceWithOil() {
        return unitPriceWithOil;
    }

    public void setUnitPriceWithOil(String unitPriceWithOil) {
        this.unitPriceWithOil = unitPriceWithOil;
    }

    @Override
    public String toString() {
        return "LogisticHistoryChargeVO{" +
                "offerId='" + offerId + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", section='" + section + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                '}';
    }
}
