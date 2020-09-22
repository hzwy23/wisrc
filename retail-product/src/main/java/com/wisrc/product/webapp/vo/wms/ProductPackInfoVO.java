package com.wisrc.product.webapp.vo.wms;

public class ProductPackInfoVO {
    private String packName;
    private int packSpec;
    private int isbase;
    private String barcode;
    private double weight;
    private String weightUnit;
    private double length;
    private double width;
    private double height;

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public int getPackSpec() {
        return packSpec;
    }

    public void setPackSpec(int packSpec) {
        this.packSpec = packSpec;
    }

    public int getIsbase() {
        return isbase;
    }

    public void setIsbase(int isbase) {
        this.isbase = isbase;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
