package com.wisrc.product.webapp.vo.productInfo.add;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

/**
 * 产品特征信息实体类
 * 单品包装规格+FBA发货规格
 */
@Data
@Valid
public class ProductSpecificationsInfoVO {

    @ApiModelProperty(value = "毛重")
    private double weight;

    @ApiModelProperty(value = "净重")
    private double netWeight;

    @ApiModelProperty(value = "包装长度")
    private double length;

    @ApiModelProperty(value = "包装宽度")
    private double width;

    @ApiModelProperty(value = "包装高度")
    private double height;

    @ApiModelProperty(value = "FBA装箱重量")
    private double fbaWeight;

    @ApiModelProperty(value = "FBA装箱长度")
    private double fbaLength;

    @ApiModelProperty(value = "FBA装箱宽度")
    private double fbaWidth;

    @ApiModelProperty(value = "FBA装箱高度")
    private double fbaHeight;

    @ApiModelProperty(value = "FBA体积重")
    private double fbaVolume;

    @ApiModelProperty(value = "装箱数量")
    private int fbaQuantity;

}
