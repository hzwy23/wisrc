package com.wisrc.purchase.webapp.dto.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@ApiModel(description = "获取到货表单")
public class GetArrivalDto {
    @ApiModelProperty(value = "到货产品")
    List<GetArrivalProductTwoDto> InspectionProduct;
    @ApiModelProperty(value = "申请单号")
    private String arrivalId;
    @ApiModelProperty(value = "日期")
    private long applyDate;
    @ApiModelProperty(value = "发起人")
    private Map employee;
    @ApiModelProperty(value = "采购订单号")
    private String orderId;
    @ApiModelProperty(value = "运费")
    private Double freight;
    @ApiModelProperty(value = "运费分摊原则")
    private Map freightApportionCd;
    @ApiModelProperty(value = "预计到货时间")
    private long expectArrivalTime;
    @ApiModelProperty(value = "运输时间-国内", required = false)
    private Integer haulageTime;
    @ApiModelProperty(value = "供应商")
    private Map supplier;
    @ApiModelProperty(value = "车牌号", required = false)
    private String plateNumber;

    @ApiModelProperty(value = "物流单号", required = false)
    private String logisticsId;
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "到货仓库")
    private String arrivalWarehouseId;

    @ApiModelProperty(value = "包材仓库")
    private String packWarehouseId;
}
