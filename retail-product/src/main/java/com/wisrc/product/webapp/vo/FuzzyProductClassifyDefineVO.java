package com.wisrc.product.webapp.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class FuzzyProductClassifyDefineVO {
    @ApiModelProperty(value = "分类中文名")
    private String classifyNameCh;
    @ApiModelProperty(value = "分类英文名")
    private String classifyNameEn;
    @ApiModelProperty(value = "分类简写")
    private String classifyShortName;
}
