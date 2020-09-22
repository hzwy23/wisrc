package com.wisrc.merchandise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MskuStatusEditVo {
    @ApiModelProperty(value = "商品状态", required = true)
    private Integer mskuStatus;
}
