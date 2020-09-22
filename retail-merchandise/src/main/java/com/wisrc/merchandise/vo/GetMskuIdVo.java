package com.wisrc.merchandise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "根据店铺和msku获取商品id")
public class GetMskuIdVo {
    @ApiModelProperty(value = "店铺名称", required = false)
    private String shopName;

    @ApiModelProperty(value = "商品编号", required = false)
    private String mskuId;
}
