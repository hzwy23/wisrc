package com.wisrc.order.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class ReSendReasonEnity {

    @ApiModelProperty(value = "重发原因ID", required = false, hidden = true)
    private Integer redeliveryTypeCd;
    @ApiModelProperty(value = "重发原因", required = true)
    private String redeliveryTypeName;

    public Integer getRedeliveryTypeCd() {
        return redeliveryTypeCd;
    }

    public void setRedeliveryTypeCd(Integer redeliveryTypeCd) {
        this.redeliveryTypeCd = redeliveryTypeCd;
    }

    public String getRedeliveryTypeName() {
        return redeliveryTypeName;
    }

    public void setRedeliveryTypeName(String redeliveryTypeName) {
        this.redeliveryTypeName = redeliveryTypeName;
    }
}
