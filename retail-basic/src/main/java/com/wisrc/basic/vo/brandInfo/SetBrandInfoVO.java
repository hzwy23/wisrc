package com.wisrc.basic.vo.brandInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class SetBrandInfoVO {
    @NotBlank(message = "品牌编码brandId不能为空")
    @ApiModelProperty(value = "品牌ID", required = true)
    private String brandId;

    @NotBlank(message = "品牌名称brandName不能为空")
    @ApiModelProperty(value = "品牌名称", required = true)
    private String brandName;

    @NotNull(message = "品牌状态statusCd不能为空")
    @ApiModelProperty(value = "品牌状态", required = true)
    private Integer statusCd;

    @ApiModelProperty(value = "图片地址", required = false)
    private String logoUrl;

    @NotNull(message = "品牌类型brandName不能为空")
    @ApiModelProperty(value = "品牌类型", required = true)
    private Integer brandType;

}
