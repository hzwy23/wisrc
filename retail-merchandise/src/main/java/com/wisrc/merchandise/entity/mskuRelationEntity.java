package com.wisrc.merchandise.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class mskuRelationEntity {
    @ApiModelProperty(value = "商品id")
    private String id;
    @ApiModelProperty(value = "商品编号")
    private String mskuId;
    @ApiModelProperty(value = "sku编号")
    private String skuId;
    @ApiModelProperty(value = "销售状态")
    private Integer salesStatusCd;
    @ApiModelProperty(value = "asin编号")
    private String asin;
    @ApiModelProperty(value = "店铺名称")
    private String shopName;
}
