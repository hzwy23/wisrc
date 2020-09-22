package com.wisrc.warehouse.webapp.vo.syncVO;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

public class MskuInfoVO {

    @ApiModelProperty(value = "商品mskuId")
    private String msku;
    @ApiModelProperty(value = "库存sku")
    private String storeSku;
    @ApiModelProperty(value = "商品名称")
    private String mskuName;
    @ApiModelProperty(value = "库存名称")
    private String storeName;
    @ApiModelProperty(value = "fnsku")
    private String FnSKU;
    @ApiModelProperty(value = "asin")
    private String ASIN;
    @ApiModelProperty(value = "asin")
    private String hsCode;
    @ApiModelProperty(value = "产品特性")
    private List<Map<String, Object>> storeLabel;//产品特性，待定字段
    @ApiModelProperty(value = "销售状态")
    private String salesStatus;
    @ApiModelProperty(value = "商品负责人")
    private String manager;
    @ApiModelProperty(value = "商品图片")
    private String picture;
    @ApiModelProperty(value = "商铺名称")
    private String shopName;
    @ApiModelProperty(value = "清关名称中文")
    private String declareNameZh;
    @ApiModelProperty(value = "清关名称英文")
    private String declareNameEn;
    @ApiModelProperty(value = "海关编号")
    private String customsNumber;
    @ApiModelProperty(value = "产品名称")
    private String productName;

    public String getHsCode() {
        return hsCode;
    }

    public void setHsCode(String hsCode) {
        this.hsCode = hsCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMsku() {
        return msku;
    }

    public void setMsku(String msku) {
        this.msku = msku;
    }

    public String getStoreSku() {
        return storeSku;
    }

    public void setStoreSku(String storeSku) {
        this.storeSku = storeSku;
    }

    public String getMskuName() {
        return mskuName;
    }

    public void setMskuName(String mskuName) {
        this.mskuName = mskuName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getFnSKU() {
        return FnSKU;
    }

    public void setFnSKU(String fnSKU) {
        FnSKU = fnSKU;
    }

    public String getASIN() {
        return ASIN;
    }

    public void setASIN(String ASIN) {
        this.ASIN = ASIN;
    }

    public List<Map<String, Object>> getStoreLabel() {
        return storeLabel;
    }

    public void setStoreLabel(List<Map<String, Object>> storeLabel) {
        this.storeLabel = storeLabel;
    }

    public String getSalesStatus() {
        return salesStatus;
    }

    public void setSalesStatus(String salesStatus) {
        this.salesStatus = salesStatus;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDeclareNameZh() {
        return declareNameZh;
    }

    public void setDeclareNameZh(String declareNameZh) {
        this.declareNameZh = declareNameZh;
    }

    public String getDeclareNameEn() {
        return declareNameEn;
    }

    public void setDeclareNameEn(String declareNameEn) {
        this.declareNameEn = declareNameEn;
    }

    public String getCustomsNumber() {
        return customsNumber;
    }

    public void setCustomsNumber(String customsNumber) {
        this.customsNumber = customsNumber;
    }

}
