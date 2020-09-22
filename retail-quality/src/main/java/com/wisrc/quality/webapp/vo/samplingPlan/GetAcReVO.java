package com.wisrc.quality.webapp.vo.samplingPlan;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class GetAcReVO {

    @Range(min = 1, max = 3, message = "检验水平编码为1到3的整数")
    @ApiModelProperty(value = "抽样方案编码", required = true)
    private Integer samplingPlanCd;

    @NotBlank(message = "样本字码[code]不能为空")
    @ApiModelProperty(value = "样本字码[code]", required = true)
    private String code;

    @NotNull(message = "[AQL]不能为空")
    @ApiModelProperty(value = "AQL", required = true)
    private Double AQL;
}
