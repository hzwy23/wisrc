package com.wisrc.product.webapp.vo.productInfo.add;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

/**
 * 用户存储产品详细描述信息
 */
@Data
@Valid
public class ProductDetailsInfoVO {
    @ApiModelProperty(value = "产品描述")
    private String description;
}
