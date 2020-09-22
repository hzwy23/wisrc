package com.wisrc.order.webapp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class NvoiceOrderDto extends NvoiceOrderAbstract {
    @ApiModelProperty(value = "创建时间", position = 5)
    private String createTime;

    @ApiModelProperty(value = "总重量（kg）", position = 6)
    private BigDecimal totalWeight;
}
