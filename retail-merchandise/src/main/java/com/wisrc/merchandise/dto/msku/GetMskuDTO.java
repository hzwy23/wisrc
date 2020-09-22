package com.wisrc.merchandise.dto.msku;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetMskuDTO {
    @ApiModelProperty(value = "商品sku编号")
    private String mskuId;

    @ApiModelProperty(value = "店铺")
    private String shopId;

    @ApiModelProperty(value = "父级asin")
    private String parentAsin;

    @ApiModelProperty(value = "商品名称")
    private String mskuName;

    @ApiModelProperty(value = "产品编号")
    private String storeSku;

    @ApiModelProperty(value = "小组")
    private String group;

    @ApiModelProperty(value = "负责人")
    private String employee;

    @ApiModelProperty(value = "销售状态")
    private Integer salesStatusCd;

    @ApiModelProperty(value = "asin")
    private String asin;

    @ApiModelProperty(value = "fnsku")
    private String fnsku;

    @ApiModelProperty(value = "配送方式")
    private String delivery;
//    @ApiModelProperty(value = "配送方式名称")
//    private String deliveryModeDesc;
    @ApiModelProperty(value = "seller编号")
    private String sellerId;

    @ApiModelProperty(value = "亚马逊佣金")
    private Double commission;
}
