package com.wisrc.order.webapp.entity;


import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

public class OrderCustomerInfoEntity {

    private String orderId;
    @ApiModelProperty(value = "客户Id", required = false)
    private String customerId;
    @ApiModelProperty(value = "收货人", required = true)
    @NotEmpty(message = "收货人不能为空")
    private String consignee;
    @ApiModelProperty(value = "邮编", required = true)
    @NotEmpty(message = "邮编不能为空")
    private String zipCode;
    @ApiModelProperty(value = "联系方式1", required = true)
    @NotEmpty(message = "联系方式1不能为空")
    private String contactOne;
    @ApiModelProperty(value = "联系方式2", required = false)
    private String contactTwo;
    @ApiModelProperty(value = "邮箱", required = false)
    private String mailbox;
    @ApiModelProperty(value = "国家", required = true)
    @NotEmpty(message = "国家不能为空")
    private String countryCd;
    @ApiModelProperty(value = "省份", required = false)
    private String provinceName;
    @ApiModelProperty(value = "城市", required = false)
    private String cityName;
    @ApiModelProperty(value = "地址", required = true)
    @NotEmpty(message = "地址不能为空")
    private String detailsAddr;
    @ApiModelProperty(value = "买家留言", required = false)
    private String buyerMessage;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }


    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }


    public String getContactOne() {
        return contactOne;
    }

    public void setContactOne(String contactOne) {
        this.contactOne = contactOne;
    }


    public String getContactTwo() {
        return contactTwo;
    }

    public void setContactTwo(String contactTwo) {
        this.contactTwo = contactTwo;
    }


    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }


    public String getCountryCd() {
        return countryCd;
    }

    public void setCountryCd(String countryCd) {
        this.countryCd = countryCd;
    }


    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    public String getDetailsAddr() {
        return detailsAddr;
    }

    public void setDetailsAddr(String detailsAddr) {
        this.detailsAddr = detailsAddr;
    }


    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }


    @Override
    public String toString() {
        return "OrderCustomerInfo{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", consignee='" + consignee + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", contactOne='" + contactOne + '\'' +
                ", contactTwo='" + contactTwo + '\'' +
                ", mailbox='" + mailbox + '\'' +
                ", countryCd='" + countryCd + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", detailsAddr='" + detailsAddr + '\'' +
                ", buyerMessage='" + buyerMessage + '\'' +
                '}';
    }
}
