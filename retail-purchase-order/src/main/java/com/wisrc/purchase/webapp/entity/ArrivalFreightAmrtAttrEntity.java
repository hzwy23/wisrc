package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

public class ArrivalFreightAmrtAttrEntity {
    @ApiModelProperty(value = "运费分摊原则id")
    private int freightApportionCd;
    @ApiModelProperty(value = "运费分摊原则类型")
    private String freightApportionDesc;

    public int getFreightApportionCd() {
        return freightApportionCd;
    }

    public void setFreightApportionCd(int freightApportionCd) {
        this.freightApportionCd = freightApportionCd;
    }

    public String getFreightApportionDesc() {
        return freightApportionDesc;
    }

    public void setFreightApportionDesc(String freightApportionDesc) {
        this.freightApportionDesc = freightApportionDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrivalFreightAmrtAttrEntity that = (ArrivalFreightAmrtAttrEntity) o;
        return freightApportionCd == that.freightApportionCd &&
                Objects.equals(freightApportionDesc, that.freightApportionDesc);
    }

    @Override
    public int hashCode() {

        return Objects.hash(freightApportionCd, freightApportionDesc);
    }
}
