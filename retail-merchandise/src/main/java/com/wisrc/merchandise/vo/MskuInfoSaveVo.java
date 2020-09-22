package com.wisrc.merchandise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(description = "保存商品")
public class MskuInfoSaveVo extends MskuInfoVo {
    @ApiModelProperty(value = "店铺id", required = true)
    @NotEmpty
    private String shopId;

    @ApiModelProperty(value = "商品sku编号", required = true)
    @NotEmpty
    private String msku;

    @ApiModelProperty(value = "配送方式", required = false)
    private String deliveryTypeDesc;
}
