package com.wisrc.rules.webapp.vo.orderExcept;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class OrderExceptVo {
    @ApiModelProperty(value = "异常类型", required = true)
    @NotEmpty(message = "异常类型不能为空")
    private String exceptTypeName;

    @ApiModelProperty(value = "异常字段", required = true)
    @NotEmpty(message = "异常字段不能为空")
    private String condColumn;

    @ApiModelProperty(value = "参数", required = true)
    @NotEmpty(message = "参数不能为空")
    private String condValue;

    @ApiModelProperty(value = "说明", required = true)
    private String description;

    @ApiModelProperty(value = "启动/禁用", required = true)
    @NotNull(message = "状态不能为空")
    private Integer statusCd;
}
