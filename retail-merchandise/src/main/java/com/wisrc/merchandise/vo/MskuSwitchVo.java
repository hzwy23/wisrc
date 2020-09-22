package com.wisrc.merchandise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "msku状态切换操作")
public class MskuSwitchVo {
    @ApiModelProperty(value = "商品id", required = true)
    private String id;

    @ApiModelProperty(value = "msku状态", required = true)
    private Integer mskuStatus;
}
