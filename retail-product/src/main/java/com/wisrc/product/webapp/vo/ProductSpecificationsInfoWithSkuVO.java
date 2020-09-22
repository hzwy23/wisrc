package com.wisrc.product.webapp.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import javax.validation.constraints.Min;

/**
 * 产品特征信息实体类
 * 单品包装规格+FBA发货规格
 */
@Data
@ApiModel
public class ProductSpecificationsInfoWithSkuVO {
    @ApiModelProperty(value = "商品SKU")
    private String skuId;

    @ApiModelProperty(value = "包装重量")
    private double weight;

    @Min(value = 0, message = "无效的参数[length]")
    @ApiModelProperty(value = "包装长度")
    private double length;

    @Min(value = 0, message = "无效的参数[width]")
    @ApiModelProperty(value = "包装宽度")
    private double width;

    @Min(value = 0, message = "无效的参数[height]")
    @ApiModelProperty(value = "包装高度")
    private double height;

    @Min(value = 0, message = "无效的参数[fbaWeight]")
    @ApiModelProperty(value = "FBA装箱重量")
    private double fbaWeight;

    @Min(value = 0, message = "无效的参数[fbaLength]")
    @ApiModelProperty(value = "FBA装箱长度")
    private double fbaLength;

    @Min(value = 0, message = "无效的参数[fbaWidth]")
    @ApiModelProperty(value = "FBA装箱宽度")
    private double fbaWidth;

    @Min(value = 0, message = "无效的参数[fbaHeight]")
    @ApiModelProperty(value = "FBA装箱高度")
    private double fbaHeight;

    @Min(value = 0, message = "无效的参数[fbaVolume]")
    @ApiModelProperty(value = "FBA体积重")
    private double fbaVolume;

    @Min(value = 0, message = "无效的参数[fbaQuantity]")
    @ApiModelProperty(value = "产品描述")
    private int fbaQuantity;
}
