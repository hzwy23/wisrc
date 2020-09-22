package com.wisrc.merchandise.dto.msku;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MskuSearchDto {
    @ApiModelProperty(value = "商品id")
    private String id;

    @ApiModelProperty(value = "商品编号")
    private String mskuId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    @ApiModelProperty(value = "sku编号")
    private String skuId;

    @ApiModelProperty(value = "销售状态")
    private String salesStatus;

    @ApiModelProperty(value = "asin编号")
    private String asin;
}
