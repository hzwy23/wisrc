package com.wisrc.order.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class ReSendOrderProductDetaiEnity {
    @NotEmpty
    private String commodityId;
    @Min(value = 0, message = "只能为正整数")
    private Integer redeliveryQuantity;
    @ApiModelProperty(value = "重发单号", hidden = true)
    private String redeliveryId;
    @ApiModelProperty(value = "uuid", hidden = true)
    private String uuid;

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getRedeliveryQuantity() {
        return redeliveryQuantity;
    }

    public void setRedeliveryQuantity(Integer redeliveryQuantity) {
        this.redeliveryQuantity = redeliveryQuantity;
    }

    public String getRedeliveryId() {
        return redeliveryId;
    }

    public void setRedeliveryId(String redeliveryId) {
        this.redeliveryId = redeliveryId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
