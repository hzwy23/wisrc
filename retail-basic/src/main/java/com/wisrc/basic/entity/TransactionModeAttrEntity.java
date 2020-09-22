package com.wisrc.basic.entity;

import io.swagger.annotations.ApiModelProperty;

public class TransactionModeAttrEntity {
    @ApiModelProperty(value = "交易模式ID(0--全选，1--FOB(默认),2--CIF,3--C&F,4--其他)", required = true)
    private int transactionModeCd;
    @ApiModelProperty(value = "交易模式名称", required = true)
    private String transactionModeName;

    public int getTransactionModeCd() {
        return transactionModeCd;
    }

    public void setTransactionModeCd(int transactionModeCd) {
        this.transactionModeCd = transactionModeCd;
    }

    public String getTransactionModeName() {
        return transactionModeName;
    }

    public void setTransactionModeName(String transactionModeName) {
        this.transactionModeName = transactionModeName;
    }

    @Override
    public String toString() {
        return "TransactionModeAttrEntity{" +
                "transactionModeCd=" + transactionModeCd +
                ", transactionModeName='" + transactionModeName + '\'' +
                '}';
    }
}
