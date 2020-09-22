package com.wisrc.rules.webapp.dto.orderExcept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class OrderExceptDto {
    @ApiModelProperty(value = "异常编号")
    private String exceptTypeCd;

    @ApiModelProperty(value = "异常类型")
    private String exceptTypeName;

    @ApiModelProperty(value = "异常字段")
    private String condColumn;

    @ApiModelProperty(value = "异常字段名称")
    private String condColumnName;

    @ApiModelProperty(value = "参数")
    private String condValue;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "启动/禁用")
    private Integer statusCd;
}
