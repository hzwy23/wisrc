package com.wisrc.product.webapp.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 产品加工信息实体类
 * 如果一个产品需要其他产品加工得到，则这个产品依赖的产品存储在这个数据结构黎。
 */
@Data
@ApiModel
public class ProductMachineInfoWithSkuVO {
    @ApiModelProperty(value = "产品的SKU")
    private String skuId;
    @ApiModelProperty(value = "加工产品的依赖SKU")
    private String dependencySkuId;
    @ApiModelProperty(value = "依赖加工产品的数量")
    private Integer quantity;

}
