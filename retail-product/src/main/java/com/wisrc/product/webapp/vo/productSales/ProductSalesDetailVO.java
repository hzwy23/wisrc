package com.wisrc.product.webapp.vo.productSales;


import lombok.Data;


/**
 * 产品定义信息实体类
 */
@Data
public class ProductSalesDetailVO {
    private String skuId;
    private Integer salesStatusCd;
    private String salesStatusDesc;

    private Integer safetyStockDays;

    private Integer internationalTransportDays;

}
