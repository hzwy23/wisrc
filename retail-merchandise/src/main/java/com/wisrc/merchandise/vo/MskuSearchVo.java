package com.wisrc.merchandise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "外部搜索商品接口")
public class MskuSearchVo {
    @ApiModelProperty(value = "asin")
    private String asin;

    @ApiModelProperty(value = "产品编号")
    private String skuid;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "商品名称")
    private String mskuName;

    @ApiModelProperty(value = "商品名称")
    private Integer salesStatus;
}
