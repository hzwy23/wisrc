package com.wisrc.merchandise.dto.mskuSalesPlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SalesPlanPageDto {
    @ApiModelProperty(value = "销售状态")
    private List<Map<String, Object>> salesStatus;

    @ApiModelProperty(value = "小组选择项")
    private List<Map> teamStatus;

    @ApiModelProperty(value = "销售计划信息")
    private List<MskuSalesPlanPageDTO> planList;

    @ApiModelProperty(value = "总数据量")
    private Long totalNum;
}
