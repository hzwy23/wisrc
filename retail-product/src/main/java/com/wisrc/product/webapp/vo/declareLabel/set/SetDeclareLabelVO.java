package com.wisrc.product.webapp.vo.declareLabel.set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
@Valid
public class SetDeclareLabelVO {
    @ApiModelProperty(value = "产品申报标签编码")
    private Integer labelCd;

    @NotBlank(message = "产品申报标签描述（名称）不能为空")
    @ApiModelProperty(value = "产品申报标签描述（名称）")
    private String labelDesc;

    @NotNull(message = "标签类型不能为空")
    @ApiModelProperty(value = "标签类型 1：基础标签，2：自定义标签")
    private Integer typeCd;

}
