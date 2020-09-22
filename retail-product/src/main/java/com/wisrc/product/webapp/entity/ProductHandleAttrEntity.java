package com.wisrc.product.webapp.entity;


import lombok.Data;

/**
 * 用户存储用户对产品操作的行为码表
 */
@Data
public class ProductHandleAttrEntity extends BaseEntity {
    private Integer handleTypeCd;
    private String handleTypeDesc;

    @Override
    public String toString() {
        return "ProductHandleAttrEntity{" +
                "handleTypeCd=" + handleTypeCd +
                ", handleTypeDesc='" + handleTypeDesc + '\'' +
                '}';
    }
}
