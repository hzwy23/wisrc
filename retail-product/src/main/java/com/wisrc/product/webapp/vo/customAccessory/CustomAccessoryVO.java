package com.wisrc.product.webapp.vo.customAccessory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CustomAccessoryVO {

//    @NotNull(message = "销售信息[productSales]不能为空")
//    @Range(min = 1001, message = "自定义配件编码从1001开始")
//    @ApiModelProperty(value = "自定义配件编码", required = true)
//    private Integer accessoryCd;

    @NotBlank(message = "产品申报标签描述[labelDesc]不能为空")
    @ApiModelProperty(value = "自定义配件描述", required = true)
    private String accessoryDesc;

    private String accessoryText;

}
