package com.wisrc.purchase.webapp.vo.invoking;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ArrivalSelectorVo {
    @ApiModelProperty(value = "")
    private String purchaseOrderId;
}
