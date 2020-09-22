package com.wisrc.product.webapp.entity;

import lombok.Data;

/**
 * 产品申报标签码表实体类
 */
@Data
public class ProductDeclareLabelAttrEntity {
    private Integer labelCd;
    private String labelDesc;
    private Integer typeCd;

    @Override
    public String toString() {
        return "ProductDeclareLabelAttrEntity{" +
                "labelCd=" + labelCd +
                ", labelDesc='" + labelDesc + '\'' +
                '}';
    }
}
