package com.wisrc.product.webapp.vo.productInfo.add;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * 产品附带图片
 */
@Data
@Valid
public class ProductImagesVO {
    @ApiModelProperty(value = "产品图片地址")
    private String imageUrl;

    @Min(value = 0, message = "无效的参数[imageClassifyCd]")
    @ApiModelProperty(value = "产品图片类型")
    private Integer imageClassifyCd;

    @ApiModelProperty(value = "uid,配合前端使用，不懂请去问前端")
    private String uid;
}
