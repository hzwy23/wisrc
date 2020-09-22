package com.wisrc.rules.webapp.vo.orderExcept;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExceptEditVo extends OrderExceptVo {
    @ApiModelProperty(value = "异常编号", required = false)
    private String exceptTypeCd;
}
