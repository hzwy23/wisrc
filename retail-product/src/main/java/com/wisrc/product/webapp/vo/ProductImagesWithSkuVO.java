package com.wisrc.product.webapp.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 产品附带图片信息实体类
 */
@Data
@ApiModel
public class ProductImagesWithSkuVO {
    @ApiModelProperty(value = "产品SKU")
    private String skuId;
    @ApiModelProperty(value = "图片地址")
    private String imageUrl;
    @ApiModelProperty(value = "图片分类")
    private Integer imageClassifyCd;

    @ApiModelProperty(value = "uid,配合前端使用，不懂请去问前端")
    private String uid;
}
