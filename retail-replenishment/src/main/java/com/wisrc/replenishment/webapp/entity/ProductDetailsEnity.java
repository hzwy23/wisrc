package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductDetailsEnity {
    @ApiModelProperty(hidden = true)
    private String uuid;

    @NotEmpty(message = "店铺编码不能为空")
    @ApiModelProperty(value = "店铺编码", required = true)
    private String shopId;
    @ApiModelProperty(value = "MSKU编号", required = true)
    @NotEmpty(message = "MSKU编号不能为空")
    private String mskuId;
    @ApiModelProperty(value = "清关名称", required = true)
    @NotEmpty(message = "清关名称不能为空")
    private String clearanceName;
    @ApiModelProperty(value = "原产地", required = false)
    private String originPlace;
    @ApiModelProperty(value = "材质", required = false)
    private String property;
    @ApiModelProperty(value = "用途", required = false)
    private String useWay;
    @Min(value = 0, message = "数量必须为非负数")
    @ApiModelProperty(value = "数量", required = true)
    private int replenishmentQuantity;
    @ApiModelProperty(value = "申报单价($)", required = true)
    @NotNull(message = "清关名称不能为空")
    @Min(message = "申请单价为非负数", value = 0)
    private Double declareUnitPrice;
    @ApiModelProperty(value = "创建时间", required = true, hidden = true)
    private String createTime;
    @ApiModelProperty(value = "创建人", required = true, hidden = true)
    private String createUser;
    @ApiModelProperty(value = "库存SKU", required = false, hidden = true)
    private String skuId;
    @ApiModelProperty(value = "发货仓ID", required = false, hidden = true)
    private String warehouseId;
    @ApiModelProperty(value = "HS编号", required = false, hidden = true)
    private String HSId;
    @ApiModelProperty(value = "msku编程", required = false, hidden = true)
    private String mskuName;
    @ApiModelProperty(value = "SKU英文名", required = false, hidden = true)
    private String skuEnName;
    @ApiModelProperty(value = "图片路径", required = false, hidden = true)
    private String imageUrl;
    @ApiModelProperty(value = "商品主键id", required = false, hidden = true)
    private String commodityId;
    @ApiModelProperty(value = "清关小计", required = false, hidden = true)
    private double clearanceSubtotal;
    @ApiModelProperty("产品中文名")
    private String skuName;
    @ApiModelProperty("fnsku")
    private String fnSku;

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getFnSku() {
        return fnSku;
    }

    public void setFnSku(String fnSku) {
        this.fnSku = fnSku;
    }

    public String getHSId() {
        return HSId;
    }

    public void setHSId(String HSId) {
        this.HSId = HSId;
    }

    public String getMskuName() {
        return mskuName;
    }

    public void setMskuName(String mskuName) {
        this.mskuName = mskuName;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getSkuEnName() {
        return skuEnName;
    }

    public void setSkuEnName(String skuEnName) {
        this.skuEnName = skuEnName;
    }

    public double getClearanceSubtotal() {
        return clearanceSubtotal;
    }

    public void setClearanceSubtotal(double clearanceSubtotal) {
        this.clearanceSubtotal = clearanceSubtotal;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
    }

    public String getClearanceName() {
        return clearanceName;
    }

    public void setClearanceName(String clearanceName) {
        this.clearanceName = clearanceName;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getUseWay() {
        return useWay;
    }

    public void setUseWay(String useWay) {
        this.useWay = useWay;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Double getDeclareUnitPrice() {
        return declareUnitPrice;
    }

    public void setDeclareUnitPrice(Double declareUnitPrice) {
        this.declareUnitPrice = declareUnitPrice;
    }


    public int getReplenishmentQuantity() {
        return replenishmentQuantity;
    }

    public void setReplenishmentQuantity(int replenishmentQuantity) {
        this.replenishmentQuantity = replenishmentQuantity;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (!(o instanceof ProductDetailsEnity)) {

            return false;
        }
        boolean con1 = false;
        boolean con2 = false;
        boolean con3 = false;
        ProductDetailsEnity product = (ProductDetailsEnity) o;
        if (originPlace == null) {
            con1 = originPlace == product.getOriginPlace();
        } else {
            con1 = originPlace.equals(product.getOriginPlace());
        }
        if (property == null) {
            con2 = property == product.getProperty();
        } else {
            con2 = property.equals(product.getProperty());
        }
        if (useWay == null) {
            con3 = useWay == product.getUseWay();
        } else {
            con3 = useWay.equals(product.getUseWay());
        }
        return product.getClearanceName().equals(clearanceName) &
                product.getDeclareUnitPrice().equals(declareUnitPrice) &
                con1 & con2 & con3;
    }

    //Idea from effective Java : Item 9
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + shopId.hashCode();
        result = 31 * result + mskuId.hashCode();
        return result;
    }
}
