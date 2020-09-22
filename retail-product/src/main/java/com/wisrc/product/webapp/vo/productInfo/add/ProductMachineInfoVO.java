package com.wisrc.product.webapp.vo.productInfo.add;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 产品加工信息实体类
 * 如果一个产品需要其他产品加工得到，则这个产品依赖的产品存储在这个数据结构黎。
 */
@Data
@Valid
public class ProductMachineInfoVO {

    @NotBlank(message = "加工产品的SKU不能为空")
    @ApiModelProperty(value = "加工产品的SKU")
    private String dependencySkuId;

    @ApiModelProperty(value = "加工产品的数量")
    private Integer quantity;
}
