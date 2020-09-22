package com.wisrc.product.webapp.vo.productInfo.add;

import io.swagger.annotations.ApiModelProperty;

public class packingMaterialList {
    @ApiModelProperty(value = "包材产品的SKU")
    private String dependencySkuId;

    @ApiModelProperty(value = "包材产品的数量")
    private Integer quantity;
}
