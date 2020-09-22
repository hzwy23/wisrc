package com.wisrc.product.webapp.entity;

import lombok.Data;

/**
 * 产品分类层级码表
 */
@Data
public class ProductClassifyLevelAttrEntity extends BaseEntity {
    private Integer levelCd;
    private String levelDesc;

    @Override
    public String toString() {
        return "ProductClassifyLevelAttrEntity{" +
                "levelCd=" + levelCd +
                ", levelDesc='" + levelDesc + '\'' +
                '}';
    }
}
