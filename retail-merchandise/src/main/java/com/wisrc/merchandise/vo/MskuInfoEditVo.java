package com.wisrc.merchandise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(description = "编辑商品")
public class MskuInfoEditVo extends MskuInfoVo {
    @ApiModelProperty(value = "商品Id", required = true)
    @NotEmpty
    private String id;
}
