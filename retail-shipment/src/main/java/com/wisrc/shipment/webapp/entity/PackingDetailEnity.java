package com.wisrc.shipment.webapp.entity;

import java.util.List;

public class PackingDetailEnity {
    private String skuId;
    private String brand;
    private List<String> labelDesc;
    private String material;
    private String model;
    private Double length;
    private Double width;
    private Double height;
    private Double singleWeight;


    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<String> getLabelDesc() {
        return labelDesc;
    }

    public void setLabelDesc(List<String> labelDesc) {
        this.labelDesc = labelDesc;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getSingleWeight() {
        return singleWeight;
    }

    public void setSingleWeight(Double singleWeight) {
        this.singleWeight = singleWeight;
    }
}
