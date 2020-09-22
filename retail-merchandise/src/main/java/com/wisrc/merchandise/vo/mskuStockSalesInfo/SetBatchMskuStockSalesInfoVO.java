package com.wisrc.merchandise.vo.mskuStockSalesInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(description = "批量更新fba库存与昨日前日，上前销量")
public class SetBatchMskuStockSalesInfoVO {
    @Valid
    @NotNull(message = "批量更新数组对象不能为空")
    @ApiModelProperty(value = "批量更新数组对象", required = true)
    private List<MskuStockSalesInfoVO> MskuStockSalesInfoList;
}
