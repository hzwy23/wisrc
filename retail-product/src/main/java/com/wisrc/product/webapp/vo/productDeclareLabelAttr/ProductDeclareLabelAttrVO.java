package com.wisrc.product.webapp.vo.productDeclareLabelAttr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class ProductDeclareLabelAttrVO {
    @NotNull(message = "labelCd不能为空")
    @ApiModelProperty(value = "标签编码", required = true)
    private Integer labelCd;
    @NotBlank(message = "labelDesc不能为空")
    @ApiModelProperty(value = "标签描述", required = true)
    private String labelDesc;
}
