package com.wisrc.product.webapp.vo.productInfo.add;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 产品申报标签信息实体类
 * 用来存储每一个产品的标签信息
 */
@Data
public class ProductDeclareLabelVO {
    @ApiModelProperty(value = "特性标签")
    private List<String> labelList;
}
