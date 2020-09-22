package com.wisrc.merchandise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(description = "保存墓志铭")
public class EpitaphSaveVo {
    @ApiModelProperty(value = "商品id", required = true)
    @NotEmpty
    private String id;

    @ApiModelProperty(value = "墓志铭", required = true)
    private String epitaph;
}
