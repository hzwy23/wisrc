package com.wisrc.purchase.webapp.dto.msku;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MskuInfoDTO {
    @ApiModelProperty(value = "商品id")
    private String id;

    @ApiModelProperty(value = "商品sku编号")
    private String mskuId;

    @ApiModelProperty(value = "店铺")
    private String shopId;

    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    @ApiModelProperty(value = "商品名称")
    private String mskuName;

    @ApiModelProperty(value = "产品编号")
    private String skuId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "父级asin")
    private String parentAsin;

    @ApiModelProperty(value = "小组")
    private String groupId;

    @ApiModelProperty(value = "负责人")
    private String userId;

    @ApiModelProperty(value = "销售状态id")
    private Integer salesStatusCd;

    @ApiModelProperty(value = "销售状态")
    private String salesStatusDesc;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "商品状态")
    private Integer mskuStatusCd;

    @ApiModelProperty(value = "asin")
    private String asin;

    @ApiModelProperty(value = "fnsku")
    private String fnSkuId;

    @ApiModelProperty(value = "上架时间")
    private String shelfDate;

    @ApiModelProperty(value = "配送方式id")
    private Integer deliveryMode;

    @ApiModelProperty(value = "配送方式")
    private String deliveryModeDesc;

    @ApiModelProperty(value = "图片地址")
    private String picture;
}
