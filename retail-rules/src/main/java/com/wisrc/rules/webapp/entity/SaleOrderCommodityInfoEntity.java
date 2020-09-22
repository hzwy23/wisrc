package com.wisrc.rules.webapp.entity;


import io.swagger.annotations.ApiModelProperty;

public class SaleOrderCommodityInfoEntity {
    @ApiModelProperty(value = "订单商品唯一标识")
    private String uuid;
    @ApiModelProperty(value = "订单号")
    private String orderId;
    @ApiModelProperty(value = "订单商品唯一标识")
    private String commodityId;
    @ApiModelProperty(value = "订单商品描述")
    private String attributeDesc;
    @ApiModelProperty(value = "订单商品单价")
    private Double unitPrice;
    @ApiModelProperty(value = "订单商品单价币种")
    private String unitPriceCurrency;
    @ApiModelProperty(value = "订单商品数量")
    private Integer quantity;
    @ApiModelProperty(value = "订单商品重量")
    private Double weight;
    @ApiModelProperty(value = "订单商品仓库Id")
    private String warehouseId;
    @ApiModelProperty(value = "订单商品创建时间")
    private String createTime;
    @ApiModelProperty(value = "订单商品创建人")
    private String createUser;
    @ApiModelProperty(value = "订单商品修改人")
    private String modifyUser;
    @ApiModelProperty(value = "订单商品修改时间")
    private String modifyTime;
    @ApiModelProperty(value = "订单商品删除标识")
    private Integer deleteStatus;
    @ApiModelProperty(value = "订单商品状态，1--正常，2--拦截中，3--作废")
    private Integer statusCd;


    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getAttributeDesc() {
        return attributeDesc;
    }

    public void setAttributeDesc(String attributeDesc) {
        this.attributeDesc = attributeDesc;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getUnitPriceCurrency() {
        return unitPriceCurrency;
    }

    public void setUnitPriceCurrency(String unitPriceCurrency) {
        this.unitPriceCurrency = unitPriceCurrency;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


}
