package com.wisrc.rules.webapp.vo.orderExcept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel
public class OrderExceptSaveVo {
    @ApiModelProperty(value = "异常订单规则")
    @NotEmpty(message = "请至少填入一个规则")
    @Valid
    private List<OrderExceptVo> orderExcepts;
}
