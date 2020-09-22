package com.wisrc.purchase.webapp.vo.inspection;

import com.wisrc.purchase.webapp.vo.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "到货通知单")
public class ArrivalPageVo extends PageVo {
    @ApiModelProperty(value = "到货通知单号", required = false)
    private String arrivalId;

    @ApiModelProperty(value = "录单开始日期", required = false)
    private String applyStartDate;

    @ApiModelProperty(value = "录单结束日期", required = false)
    private String applyEndDate;

    @ApiModelProperty(value = "发起人", required = false)
    private String employeeId;

    @ApiModelProperty(value = "预计到货开始时间", required = false)
    private String expectArrivalStartTime;

    @ApiModelProperty(value = "预计到货结束时间", required = false)
    private String expectArrivalEndTime;

    @ApiModelProperty(value = "采购订单号", required = false)
    private String orderId;

    @ApiModelProperty(value = "库存sku", required = false)
    private String skuId;

    @ApiModelProperty(value = "产品中文名", required = false)
    private String productName;

    @ApiModelProperty(value = "关键词(供应商)", required = false)
    private String findKey;

    @ApiModelProperty(value = "到货通知单状态", required = false)
    private Integer statusCd;
}
