package com.wisrc.quality.webapp.vo.samplingPlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class GetCodeAndQuantity {
    @Range(min = 1, max = 7, message = "检验水平编码为1到7的整数")
    @ApiModelProperty(value = "检验水平编码", required = true)
    private Integer inspectionLevelCd;

    @Range(min = 1, max = 3, message = "抽样方案编码为1到3的整数")
    @ApiModelProperty(value = "抽样方案编码", required = true)
    private Integer samplingPlanCd;

    @Range(min = 2, message = "实际检验数量必须大于1")
    @ApiModelProperty(value = "实际检验数量", required = true)
    private Integer actualInspectionQuantity;
}
