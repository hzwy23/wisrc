package com.wisrc.rules.webapp.dto.orderExcept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class OrderExceptListDto {
    @ApiModelProperty(value = "异常订单规则")
    private List<OrderExceptDto> orderExcepts;
}
