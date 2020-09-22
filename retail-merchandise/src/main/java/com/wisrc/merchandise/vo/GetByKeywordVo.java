package com.wisrc.merchandise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class GetByKeywordVo {
    @ApiModelProperty(value = "负责人（非同步参数）", required = false)
    private String employeeId;
    @ApiModelProperty(value = "产品编号", required = false)
    private String skuId;
    @ApiModelProperty(value = "产品名称", required = false)
    private String productName;
}
