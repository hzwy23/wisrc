package com.wisrc.merchandise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "删除销售整个计划")
public class DeleteSalesIdVo {
    @ApiModelProperty(value = "商品Id", required = true)
    private String id;
}
