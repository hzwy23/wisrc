package com.wisrc.merchandise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "编辑时销售计划信息")
public class MskuSalesPlanEditVo extends MskuSalesPlanSaveVo {
    @ApiModelProperty(value = "销售计划Id", required = true, name = "主键")
    private String planId;
}
