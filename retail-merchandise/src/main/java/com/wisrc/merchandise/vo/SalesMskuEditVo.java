package com.wisrc.merchandise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel(description = "编辑整个销售计划")
public class SalesMskuEditVo {
    @ApiModelProperty(value = "商品id", required = true, name = "所属库存sku")
    @NotEmpty(message = "页面出错，请联系管理员")
    private String id;

    @ApiModelProperty(value = "销售计划", required = true)
    @NotEmpty(message = "销售计划不能为空")
    @Valid
    private List<MskuSalesPlanEditVo> plans;
}
