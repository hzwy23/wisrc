package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetWaybillException {
    @ApiModelProperty(value = "异常ID")
    private int exceptionTypeCd;
    @ApiModelProperty(value = "异常描述名称")
    private String exceptionTypeDesc;
    @ApiModelProperty(value = "其他信息")
    private String other;
}
