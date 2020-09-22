package com.wisrc.order.webapp.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@ApiModel(description = "页面VO插件")
public abstract class PageVo {
    @ApiModelProperty(value = "页码", required = false)
    @Min(value = 1, message = "非法页码")
    private Integer pageNum;

    @ApiModelProperty(value = "单页数量", required = false)
    @Min(value = 1, message = "非法单页数量")
    private Integer pageSize;
}
