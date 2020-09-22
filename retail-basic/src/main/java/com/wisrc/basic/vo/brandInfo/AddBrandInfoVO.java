package com.wisrc.basic.vo.brandInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class AddBrandInfoVO {
    @NotBlank(message = "品牌名称brandName不能为空")
    @ApiModelProperty(value = "品牌名称", required = true)
    private String brandName;

    @ApiModelProperty(value = "图片地址", required = false)
    private String logoUrl;

    @NotNull(message = "品牌类型brandType不能为空")
    @ApiModelProperty(value = "品牌类型", required = true)
    private Integer brandType;


}
