package com.wisrc.product.webapp.vo.productInfo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

/**
 * 产品申报标签信息实体类
 * 用来存储每一个产品的标签信息
 */
@Data
@ApiModel
@Valid
public class NewAddProductDeclareLabelVO {
    @Valid
    @ApiModelProperty(value = "特性标签数组")
    private List<Integer> labelList;
}
