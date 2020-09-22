package com.wisrc.merchandise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(description = "验证msku和店铺")
public class CheckMskuShopVo {
    @ApiModelProperty(value = "msku", required = true)
    @NotEmpty
    private String mskuId;

    @ApiModelProperty(value = "店铺Id", required = true)
    @NotEmpty
    private String shopId;
}
