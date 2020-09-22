package com.wisrc.product.webapp.entity;


import lombok.Data;

/**
 * 产品状态码表
 */
@Data
public class ProductStatusAttrEntity extends BaseEntity {
    private Integer statusCd;
    private String statusDesc;

    @Override
    public String toString() {
        return "ProductStatusAttrEntity{" +
                "statusCd=" + statusCd +
                ", statusDesc='" + statusDesc + '\'' +
                '}';
    }
}
