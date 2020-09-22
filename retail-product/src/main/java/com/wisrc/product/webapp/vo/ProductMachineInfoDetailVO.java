package com.wisrc.product.webapp.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 */
@Data
@ApiModel
public class ProductMachineInfoDetailVO {
    private String dependencySkuId;
    private Integer quantity;
    private String skuNameZh;  //中文名
}
