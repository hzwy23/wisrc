package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;

@Api(tags = "产品交货日期与数量信息")
public class ProductDeliveryInfoEntity {
    @ApiModelProperty(value = "唯一ID", hidden = true)
    private String uuid;
    @ApiModelProperty(value = "采购订单产品ID", hidden = true)
    private String id;
    @ApiModelProperty(value = "交货日期")
    private Date deliveryTime;
    @ApiModelProperty(value = "数量")
    private int number;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ProductDeliveryInfoEntity{" +
                "uuid='" + uuid + '\'' +
                ", id='" + id + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", number='" + number + '\'' +
                '}';
    }
}
