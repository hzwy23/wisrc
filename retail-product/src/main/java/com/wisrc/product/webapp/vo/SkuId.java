package com.wisrc.product.webapp.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class SkuId {
    @ApiModelProperty(value = "skuId的集合")
    List<String> skuIdList;
}
