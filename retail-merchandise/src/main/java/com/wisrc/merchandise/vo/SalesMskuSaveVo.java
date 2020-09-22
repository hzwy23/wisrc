package com.wisrc.merchandise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel(description = "保存整个销售计划")
public class SalesMskuSaveVo {
    @ApiModelProperty(value = "库存sku", required = true, name = "所属库存sku")
    @NotEmpty(message = "msku不能为空")
    private String msku;

    @ApiModelProperty(value = "店铺Id", required = true)
    @NotEmpty(message = "店铺Id不能为空")
    private String shopId;

    @ApiModelProperty(value = "销售计划", required = true)
    @NotEmpty(message = "计划不能为空")
    @Valid
    private List<MskuSalesPlanSaveVo> plans;
}
