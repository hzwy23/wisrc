package com.wisrc.shipment.webapp.vo.msku;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BatchMskuSafetyStockDays {
    @NotEmpty
    @ApiModelProperty(value = "选中的数据的id", required = true)
    private List<String> ids;

    @NotNull
    @ApiModelProperty(value = "设置的安全库存天数", required = true)
    private Integer days;
}
