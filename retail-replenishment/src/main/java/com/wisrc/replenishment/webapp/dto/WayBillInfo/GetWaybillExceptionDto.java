package com.wisrc.replenishment.webapp.dto.WayBillInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetWaybillExceptionDto {
    @ApiModelProperty(value = "异常ID")
    private int exceptionTypeCd;
    @ApiModelProperty(value = "异常描述名称")
    private String exceptionTypeDesc;
    @ApiModelProperty(value = "其他显示信息")
    private String other;
}
