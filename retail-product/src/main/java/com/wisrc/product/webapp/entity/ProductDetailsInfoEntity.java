package com.wisrc.product.webapp.entity;


import lombok.Data;

/**
 * 用户存储产品详细描述信息
 */
@Data
public class ProductDetailsInfoEntity extends BaseEntity {
    private String skuId;
    private String description;

    @Override
    public String toString() {
        return "ProductDetailsInfoEntity{" +
                "skuId='" + skuId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
