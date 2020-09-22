package com.wisrc.product.webapp.entity;


import lombok.Data;

/**
 * 产品图片分类码表
 */
@Data
public class ProductImageClassifyAttrEntity extends BaseEntity {
    private Integer imageClassifyCd;
    private String imageClassifyDesc;

    @Override
    public String toString() {
        return "ProductImageClassifyAttrEntity{" +
                "imageClassifyCd=" + imageClassifyCd +
                ", imageClassifyDesc='" + imageClassifyDesc + '\'' +
                '}';
    }
}
