package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(tags = "订单条款")
public class OrderProvisionEntity {
    @ApiModelProperty(value = "唯一标识")
    private String uuid;
    @ApiModelProperty(value = "采购订单号")
    private String orderId;
    @ApiModelProperty(value = "条款文档存储路径")
    private String provisionUrl;
    @ApiModelProperty(value = "删除标识")
    private int deleteStatus;
    @ApiModelProperty(value = "供应商收款人")
    private String payee;
    @ApiModelProperty(value = "供应商开户银行")
    private String bank;
    @ApiModelProperty(value = "供应商银行账户")
    private String account;

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProvisionUrl() {
        return provisionUrl;
    }

    public void setProvisionUrl(String provisionUrl) {
        this.provisionUrl = provisionUrl;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @Override
    public String toString() {
        return "OrderProvisionEntity{" +
                "uuid='" + uuid + '\'' +
                ", orderId='" + orderId + '\'' +
                ", provisionUrl='" + provisionUrl + '\'' +
                ", deleteStatus=" + deleteStatus +
                ", payee='" + payee + '\'' +
                ", bank='" + bank + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}
