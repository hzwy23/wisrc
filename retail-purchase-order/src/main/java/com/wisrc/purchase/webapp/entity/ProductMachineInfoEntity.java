package com.wisrc.purchase.webapp.entity;

public class ProductMachineInfoEntity {
    private String dependencySkuId;
    private Integer quantity;
    private String skuNameZh;

    public String getDependencySkuId() {
        return dependencySkuId;
    }

    public void setDependencySkuId(String dependencySkuId) {
        this.dependencySkuId = dependencySkuId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSkuNameZh() {
        return skuNameZh;
    }

    public void setSkuNameZh(String skuNameZh) {
        this.skuNameZh = skuNameZh;
    }
}
