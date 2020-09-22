package com.wisrc.replenishment.webapp.dto.logisticsPlan;

import com.wisrc.replenishment.webapp.dto.PageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "物流计划列表")
public class LogisticsPlanPageDto extends PageDto {
    @ApiModelProperty(value = "物流计划列表数据")
    List<GetLogisticsPlanPageReturnDto> logisticsPlanList;
}
