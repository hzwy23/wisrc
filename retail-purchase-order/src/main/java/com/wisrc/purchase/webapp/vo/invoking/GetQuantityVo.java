package com.wisrc.purchase.webapp.vo.invoking;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel
public class GetQuantityVo {
    @ApiModelProperty(value = "订单id")
    @NotEmpty(message = "订单id不能为空")
    private String orderId;
    @ApiModelProperty(value = "产品id")
    @NotEmpty(message = "产品id不能为空")
    private List<String> skuId;
}
