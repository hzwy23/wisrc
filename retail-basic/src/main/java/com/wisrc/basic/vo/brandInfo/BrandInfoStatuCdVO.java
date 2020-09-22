package com.wisrc.basic.vo.brandInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class BrandInfoStatuCdVO {
    @NotBlank(message = "品牌编码[brandId]不能为空")
    @ApiModelProperty(value = "品牌编码", required = true)
    private String brandId;

    @NotNull(message = "状态编码（1，启用。2，禁用）不能为空")

    @ApiModelProperty(value = "状态编码（1，启用。2，禁用） ", required = true)
    private Integer statusCd;
}
