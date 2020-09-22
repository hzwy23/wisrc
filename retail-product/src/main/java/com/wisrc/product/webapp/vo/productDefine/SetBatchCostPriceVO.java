package com.wisrc.product.webapp.vo.productDefine;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class SetBatchCostPriceVO {
    @Valid
    @NotEmpty(message = "批量更新成本价参数集合不能为空")
    private List<CostPriceVO> costPriceVOList;
}
