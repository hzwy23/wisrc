package com.wisrc.merchandise.dto.msku;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
public class GetMskuOutsideDTO {
    @ApiModelProperty(value = "商品sku编号")
    private String mskuId;

    @ApiModelProperty(value = "店铺")
    private Map shop;

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
    private Map salesStatus;

    @ApiModelProperty(value = "asin")
    private String asin;

    @ApiModelProperty(value = "fnsku")
    private String fnsku;

    @ApiModelProperty(value = "配送方式")
    private Map delivery;

    @ApiModelProperty(value = "图片地址")
    private String picture;

    @ApiModelProperty(value = "店铺ID")
    private String shipId;
}
