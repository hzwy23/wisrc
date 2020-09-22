package com.wisrc.replenishment.webapp.vo.logisticsPlan;

import com.wisrc.replenishment.webapp.vo.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;

@Data
@ApiModel(description = "物流计划列表")
public class LogisticsPlanPageVo extends PageVo {
    @ApiModelProperty(value = "计划截止开始日期", required = false)
    private Date salesLastStartTime;

    @ApiModelProperty(value = "计划截止结束日期", required = false)
    private Date salesLastEndTime;

    @ApiModelProperty(value = "所属店铺", required = false)
    private String shopId;

    @ApiModelProperty(value = "负责人", required = false)
    private String employeeId;

    @ApiModelProperty(value = "销售状态", required = false)
    private Integer salesStatusCd;

    @ApiModelProperty(value = "关键字（ASIN/MSKU/库存SKU/产品名称）", required = false)
    private String findKey;
}
