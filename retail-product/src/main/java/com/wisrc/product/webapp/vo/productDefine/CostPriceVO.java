package com.wisrc.product.webapp.vo.productDefine;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CostPriceVO {
    @NotNull(message = "产品sku[skuId]不能为空")
    @ApiModelProperty(value = "产品sku", required = true)
    private String skuId;

    @NotNull(message = "成本价[costPrice]不能为空")
    @ApiModelProperty(value = "成本价", required = true)
    private Double costPrice;
}
