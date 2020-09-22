package com.wisrc.merchandise.entity;

import java.util.Objects;

public class MskuDeliveryModeAttrEntity {
    private int deliveryMode;
    private String deliveryModeDesc;

    public int getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(int deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public String getDeliveryModeDesc() {
        return deliveryModeDesc;
    }

    public void setDeliveryModeDesc(String deliveryModeDesc) {
        this.deliveryModeDesc = deliveryModeDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MskuDeliveryModeAttrEntity that = (MskuDeliveryModeAttrEntity) o;
        return deliveryMode == that.deliveryMode &&
                Objects.equals(deliveryModeDesc, that.deliveryModeDesc);
    }

    @Override
    public int hashCode() {

        return Objects.hash(deliveryMode, deliveryModeDesc);
    }
}
