package com.wisrc.order.webapp.entity;


import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OrderCommodityInfoEntity {

    private String uuid;
    private String orderId;
    @ApiModelProperty(value = "商品Id", required = true, hidden = false)
    @NotEmpty(message = "商品id不能为空")
    private String commodityId;
    @ApiModelProperty(value = "属性", required = false)
    private String attributeDesc;
    @ApiModelProperty(value = "商品编号", required = true)
    @NotEmpty(message = "商品编号不能为空")
    private String mskuId;
    @ApiModelProperty(value = "单价", required = true)
    @NotNull(message = "单价不能为空")
    private Double unitPrice;
    @ApiModelProperty(value = "单价币种", required = true)
    @NotEmpty(message = "单价币种不能为空")
    private String unitPriceCurrency;
    @ApiModelProperty(value = "数量", required = true)
    @NotNull(message = "数量不能为空")
    private Integer quantity;
    @ApiModelProperty(value = "重量", required = false)
    private double weight;
    @ApiModelProperty(value = "仓库", required = false)
    private String warehouseId;
    @ApiModelProperty(value = "商品状态", required = true)
    private int statusCd;
    @ApiModelProperty(value = "创建时间", required = false, hidden = true)
    private String createTime;
    @ApiModelProperty(value = "创建人", required = false, hidden = true)
    private String createUser;
    @ApiModelProperty(value = "更新人", required = false, hidden = true)
    private String modifyUser;
    @ApiModelProperty(value = "更新时间", required = false, hidden = true)
    private String modifyTime;
    @ApiModelProperty(value = "图片", required = false, hidden = true)
    private String picture;
    @ApiModelProperty(value = "库存Sku", required = false, hidden = true)
    private String skuId;
    @ApiModelProperty(value = "商品名称", required = false, hidden = true)
    private String mskuName;
    @ApiModelProperty(value = "产品中文名", required = false, hidden = true)
    private String skuNameZh;
    @ApiModelProperty(value = "仓库名称", hidden = true)
    private String warehouseName;
    @ApiModelProperty(value = "可用库存", hidden = true)
    private Integer enableStock;
    @ApiModelProperty(value = "状态", hidden = true)
    private String statusDesc;
    private int deleteStatus;

    public String getMskuName() {
        return mskuName;
    }

    public void setMskuName(String mskuName) {
        this.mskuName = mskuName;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
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

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuNameZh() {
        return skuNameZh;
    }

    public void setSkuNameZh(String skuNameZh) {
        this.skuNameZh = skuNameZh;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Integer getEnableStock() {
        return enableStock;
    }

    public void setEnableStock(Integer enableStock) {
        this.enableStock = enableStock;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    @Override
    public String toString() {
        return "OrderCommodityInfo{" +
                "uuid='" + uuid + '\'' +
                ", orderId='" + orderId + '\'' +
                ", commodityId='" + commodityId + '\'' +
                ", attributeDesc='" + attributeDesc + '\'' +
                ", unitPrice=" + unitPrice +
                ", unitPriceCurrency='" + unitPriceCurrency + '\'' +
                ", quantity=" + quantity +
                ", weight=" + weight +
                ", warehouseId='" + warehouseId + '\'' +
                ", statusCd=" + statusCd +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyTime=" + modifyTime +
                ", deleteStatus=" + deleteStatus +
                '}';
    }
}
