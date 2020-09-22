package com.wisrc.basic.entity;

import io.swagger.annotations.ApiModelProperty;

public class TradeModeAttrEntity {
    @ApiModelProperty(value = "贸易方式ID(0--全选，1--一般贸易（默认）)", required = true)
    private int tradeModeCd;
    @ApiModelProperty(value = "贸易方式名称", required = true)
    private String tradeModeName;

    public int getTradeModeCd() {
        return tradeModeCd;
    }

    public void setTradeModeCd(int tradeModeCd) {
        this.tradeModeCd = tradeModeCd;
    }

    public String getTradeModeName() {
        return tradeModeName;
    }

    public void setTradeModeName(String tradeModeName) {
        this.tradeModeName = tradeModeName;
    }

    @Override
    public String toString() {
        return "TradeModeAttrEntity{" +
                "tradeModeCd=" + tradeModeCd +
                ", tradeModeName='" + tradeModeName + '\'' +
                '}';
    }
}
