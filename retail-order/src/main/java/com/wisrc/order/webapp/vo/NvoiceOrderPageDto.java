package com.wisrc.order.webapp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class NvoiceOrderPageDto extends PageDto {
    @ApiModelProperty(value = "所有发货单数据")
    private List<NvoiceOrderDto> nvoiceOrders;
}
