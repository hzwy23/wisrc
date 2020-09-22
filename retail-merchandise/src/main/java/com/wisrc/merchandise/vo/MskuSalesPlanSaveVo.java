package com.wisrc.merchandise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(description = "销售计划信息")
public class MskuSalesPlanSaveVo {
    @ApiModelProperty(value = "预计日销量", required = true)
    @NotNull(message = "预计日销量不能为空")
    @Min(value = 1)
    private Integer exptDailySales;

    @ApiModelProperty(value = "指导标准价", required = true)
    @NotNull(message = "指导标准价不能为空")
    private Double guidePrice;

    @ApiModelProperty(value = "销售状态编码", required = true)
    @NotNull(message = "销售状态编码不能为空")
    private Integer salesStatus;

    @ApiModelProperty(value = "开始时间", required = true)
    @NotNull(message = "开始时间不能为空")
    private Date startDate;

    @ApiModelProperty(value = "结束时间", required = true)
    private Date expiryDate;

}
