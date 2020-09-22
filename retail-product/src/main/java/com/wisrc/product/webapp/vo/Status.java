package com.wisrc.product.webapp.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class Status {
    @ApiModelProperty(value = "产品SKU")
    private String skuId;

    @ApiModelProperty(value = "状态值")
    private Integer statusCd;
}
