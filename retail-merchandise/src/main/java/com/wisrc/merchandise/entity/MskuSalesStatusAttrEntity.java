package com.wisrc.merchandise.entity;

import java.util.Objects;

public class MskuSalesStatusAttrEntity {
    private int salesStatusCd;
    private String salesStatusDesc;

    public int getSalesStatusCd() {
        return salesStatusCd;
    }

    public void setSalesStatusCd(int salesStatusCd) {
        this.salesStatusCd = salesStatusCd;
    }

    public String getSalesStatusDesc() {
        return salesStatusDesc;
    }

    public void setSalesStatusDesc(String salesStatusDesc) {
        this.salesStatusDesc = salesStatusDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MskuSalesStatusAttrEntity that = (MskuSalesStatusAttrEntity) o;
        return salesStatusCd == that.salesStatusCd &&
                Objects.equals(salesStatusDesc, that.salesStatusDesc);
    }

    @Override
    public int hashCode() {

        return Objects.hash(salesStatusCd, salesStatusDesc);
    }
}
