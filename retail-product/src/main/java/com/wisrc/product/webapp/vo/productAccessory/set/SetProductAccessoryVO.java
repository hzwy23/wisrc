package com.wisrc.product.webapp.vo.productAccessory.set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
@Valid
public class SetProductAccessoryVO {

    @ApiModelProperty(value = "产品配件编码")
    private Integer accessoryCd;

    @NotBlank(message = "产品配件描述")
    @ApiModelProperty(value = "产品配件描述")
    private String accessoryDesc;

    @NotNull(message = "配件类型不能为空")
    @ApiModelProperty(value = "配件类型 1：基础配件，2：自定义配件")
    private Integer typeCd;
}
