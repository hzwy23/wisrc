package com.wisrc.basic.entity;

import io.swagger.annotations.ApiModelProperty;

public class MonetarySystemAttrEntity {
    @ApiModelProperty(value = "货币制度ID(0--全选，1--USD（默认），2--HKD)", required = true)
    private int monetarySystemCd;
    @ApiModelProperty(value = "货币制度名称", required = true)
    private String monetarySystemName;

    public int getMonetarySystemCd() {
        return monetarySystemCd;
    }

    public void setMonetarySystemCd(int monetarySystemCd) {
        this.monetarySystemCd = monetarySystemCd;
    }

    public String getMonetarySystemName() {
        return monetarySystemName;
    }

    public void setMonetarySystemName(String monetarySystemName) {
        this.monetarySystemName = monetarySystemName;
    }

    @Override
    public String toString() {
        return "MonetarySystemAttrEntity{" +
                "monetarySystemCd=" + monetarySystemCd +
                ", monetarySystemName='" + monetarySystemName + '\'' +
                '}';
    }
}
