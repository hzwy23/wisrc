package com.wisrc.product.webapp.entity;


import lombok.Data;

/**
 * 产品特征信息实体类
 * 单品包装规格+FBA发货规格
 */
@Data
public class ProductSpecificationsInfoEntity extends BaseEntity {
    private String skuId;
    private double length;
    private double width;
    private double height;
    private double weight; //毛重
    private double netWeight;//净重

    private double fbaWeight;
    private double fbaLength;
    private double fbaWidth;
    private double fbaHeight;
    private double fbaVolume;
    private int fbaQuantity;


}
