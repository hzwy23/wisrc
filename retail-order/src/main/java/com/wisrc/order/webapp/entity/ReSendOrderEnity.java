package com.wisrc.order.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ReSendOrderEnity {
    @NotEmpty(message = "订单号不能为空")
    @ApiModelProperty(value = "订单号")
    private String orderId;
    @ApiModelProperty(value = "重发Id", hidden = true)
    private String redeliveryId;
    @NotEmpty(message = "发货渠道不能为空")
    @ApiModelProperty(value = "发货渠道")
    private String offerId;
    @ApiModelProperty(value = "重发原因")
    @NotNull(message = "重发原因不能为空")
    private Integer redeliveryTypeCd;
    @ApiModelProperty(value = "物流单号")
    @NotEmpty(message = "物流单号不能为空")
    private String logisticsId;
    @ApiModelProperty(value = "物流费用")
    @NotNull(message = "物流单号不能为空")
    private Double logisticsCost;
    @ApiModelProperty(value = "创建人", required = false, hidden = true)
    private String createUser;
    @ApiModelProperty(value = "创建时间", required = false, hidden = true)
    private String createTime;
    @ApiModelProperty(value = "修改人", required = false, hidden = true)
    private String modifyUser;
    @ApiModelProperty(value = "修改时间", required = false, hidden = true)
    private String modifyTime;
    @ApiModelProperty(value = "uuid", hidden = true)
    private String uuid;
    @NotEmpty
    @Valid
    private List<ReSendOrderProductDetaiEnity> productDetailInfo;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public Integer getRedeliveryTypeCd() {
        return redeliveryTypeCd;
    }

    public void setRedeliveryTypeCd(Integer redeliveryTypeCd) {
        this.redeliveryTypeCd = redeliveryTypeCd;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public List<ReSendOrderProductDetaiEnity> getProductDetailInfo() {
        return productDetailInfo;
    }

    public void setProductDetailInfo(List<ReSendOrderProductDetaiEnity> productDetailInfo) {
        this.productDetailInfo = productDetailInfo;
    }


    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Double getLogisticsCost() {
        return logisticsCost;
    }

    public void setLogisticsCost(Double logisticsCost) {
        this.logisticsCost = logisticsCost;
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
