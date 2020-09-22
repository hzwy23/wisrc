package com.wisrc.purchase.webapp.vo.inspection;

import com.wisrc.purchase.webapp.vo.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "到货通知单")
public class InspectionPageVo extends PageVo {
    @ApiModelProperty(value = "到货通知单号", required = false)
    private String arrivalId;

    @ApiModelProperty(value = "单据开始日期", required = false)
    private String applyStartDate;

    @ApiModelProperty(value = "单据结束日期", required = false)
    private String applyEndDate;

    @ApiModelProperty(value = "发起人", required = false)
    private String employeeId;

    @ApiModelProperty(value = "预计到货开始时间", required = false)
    private String expectArrivalStartTime;

    @ApiModelProperty(value = "预计到货结束时间", required = false)
    private String expectArrivalEndTime;

    @ApiModelProperty(value = "采购订单号", required = false)
    private String orderId;

    @ApiModelProperty(value = "SKU", required = false)
    private String skuId;

    @ApiModelProperty(value = "物流单号", required = false)
    private String logisticsId;

    @ApiModelProperty(value = "关键词(供应商\\产品中文名)", required = false)
    private String findKey;

    @ApiModelProperty(value = "到货通知单状态", required = false)
    private Integer statusCd;
}
