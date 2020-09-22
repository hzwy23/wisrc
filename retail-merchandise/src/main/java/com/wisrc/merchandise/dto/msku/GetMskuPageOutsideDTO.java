package com.wisrc.merchandise.dto.msku;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "商品信息列表关联主数据")
public class GetMskuPageOutsideDTO {
    @ApiModelProperty(value = "商品编号")
    private String id;

    @ApiModelProperty(value = "商品sku")
    private String mskuId;

    @ApiModelProperty(value = "商品名称")
    private String mskuName;

    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    @ApiModelProperty(value = "产品编号")
    private String skuId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "负责人")
    private String employee;

    @ApiModelProperty(value = "销售状态")
    private String salesStatus;

    @ApiModelProperty(value = "asin")
    private String asin;

    @ApiModelProperty(value = "fnsku")
    private String fnsku;

    @ApiModelProperty(value = "商品图片")
    private String picture;

    @ApiModelProperty(value = "店铺ID")
    private String shopId;
}
