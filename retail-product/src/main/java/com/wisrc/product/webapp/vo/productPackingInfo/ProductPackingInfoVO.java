package com.wisrc.product.webapp.vo.productPackingInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

@Data
@ApiModel
@Valid
public class ProductPackingInfoVO {
    //新增需求
    @ApiModelProperty(value = "装箱长度")
    private double packLength;

    @ApiModelProperty(value = "装箱高度")
    private double packHeight;

    @ApiModelProperty(value = "装箱宽度")
    private double packWidth;

    @ApiModelProperty(value = "装箱数量")
    private int packQuantity;

    @ApiModelProperty(value = "装箱重量")
    private double packWeight;
}
