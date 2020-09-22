package com.wisrc.merchandise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "根据商品编号和名称获取商品id")
public class GetByMskuIdAndNameVo {
    @ApiModelProperty(value = "商品编号", required = false)
    private String mskuId;

    @ApiModelProperty(value = "商品名称", required = false)
    private String mskuName;
}
