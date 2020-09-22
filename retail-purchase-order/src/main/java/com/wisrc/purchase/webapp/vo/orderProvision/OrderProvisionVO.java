package com.wisrc.purchase.webapp.vo.orderProvision;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

@Api(tags = "订单条款")
public class OrderProvisionVO {
    @ApiModelProperty(value = "唯一标识（修改时才传参）")
    private String uuid;
    @ApiModelProperty(value = "订单号ID")
    private String orderId;
    @ApiModelProperty(value = "条款内容")
    private String provisionContent;
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

    public String getProvisionContent() {
        return provisionContent;
    }

    public void setProvisionContent(String provisionContent) {
        this.provisionContent = provisionContent;
    }
}
