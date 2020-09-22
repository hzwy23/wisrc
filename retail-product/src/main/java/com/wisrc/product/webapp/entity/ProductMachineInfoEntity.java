package com.wisrc.product.webapp.entity;

import lombok.Data;

/**
 * 产品加工信息实体类
 * 如果一个产品需要其他产品加工得到，则这个产品依赖的产品存储在这个数据结构黎。
 */
@Data
public class ProductMachineInfoEntity extends BaseEntity {
    private String uuid;
    private String skuId;
    private String dependencySkuId;
    private Integer quantity;
    private Integer typeCd;

}
