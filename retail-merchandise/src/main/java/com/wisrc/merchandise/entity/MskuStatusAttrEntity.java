package com.wisrc.merchandise.entity;

import java.util.Objects;

public class MskuStatusAttrEntity {
    private int mskuStatusCd;
    private String mskuStatusDesc;

    public int getMskuStatusCd() {
        return mskuStatusCd;
    }

    public void setMskuStatusCd(int mskuStatusCd) {
        this.mskuStatusCd = mskuStatusCd;
    }

    public String getMskuStatusDesc() {
        return mskuStatusDesc;
    }

    public void setMskuStatusDesc(String mskuStatusDesc) {
        this.mskuStatusDesc = mskuStatusDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MskuStatusAttrEntity that = (MskuStatusAttrEntity) o;
        return mskuStatusCd == that.mskuStatusCd &&
                Objects.equals(mskuStatusDesc, that.mskuStatusDesc);
    }

    @Override
    public int hashCode() {

        return Objects.hash(mskuStatusCd, mskuStatusDesc);
    }
}
