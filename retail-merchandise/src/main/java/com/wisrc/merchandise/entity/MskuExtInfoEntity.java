package com.wisrc.merchandise.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;

@Data
public class MskuExtInfoEntity {
    private String id;
    private Timestamp shelfDate;
    private String fnSkuId;
    private String asin;
    private Integer deliveryMode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getShelfDate() {
        return shelfDate;
    }

    public void setShelfDate(Timestamp shelfDate) {
        this.shelfDate = shelfDate;
    }

    public String getFnSkuId() {
        return fnSkuId;
    }

    public void setFnSkuId(String fnSkuId) {
        this.fnSkuId = fnSkuId;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public Integer getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(Integer deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MskuExtInfoEntity that = (MskuExtInfoEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(shelfDate, that.shelfDate) &&
                Objects.equals(fnSkuId, that.fnSkuId) &&
                Objects.equals(asin, that.asin);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, shelfDate, fnSkuId, asin);
    }
}
