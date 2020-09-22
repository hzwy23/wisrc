package com.wisrc.merchandise.entity;

import java.util.Objects;

public class MskuSalesDefineEntity {
    private String planId;
    private String id;
    private int mskuStatusCd;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMskuStatusCd() {
        return mskuStatusCd;
    }

    public void setMskuStatusCd(int mskuStatusCd) {
        this.mskuStatusCd = mskuStatusCd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MskuSalesDefineEntity that = (MskuSalesDefineEntity) o;
        return Objects.equals(planId, that.planId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(planId);
    }
}
