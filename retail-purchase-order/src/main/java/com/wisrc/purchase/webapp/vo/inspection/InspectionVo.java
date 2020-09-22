package com.wisrc.purchase.webapp.vo.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(description = "验货表单")
public class InspectionVo {
    @ApiModelProperty(value = "申请人", required = true)
    @NotEmpty(message = "申请人不能为空")
    private String employeeId;

    @ApiModelProperty(value = "供应商", required = true)
    @NotEmpty(message = "供应商不能为空")
    private String supplierId;

    @ApiModelProperty(value = "运输时间-国内", required = false)
    private Integer haulageTime;

    @ApiModelProperty(value = "日期", required = true)
    @NotNull(message = "日期不能为空")
    private Date applyDate;

    @ApiModelProperty(value = "采购订单号", required = true)
    @NotEmpty(message = "采购订单号不能为空")
    private String orderId;

    @ApiModelProperty(value = "预计到货时间", required = false)
    private Date expectArrivalTime;

    @ApiModelProperty(value = "运费", required = false)
    //@PositiveMaxDouble(message = "运费必须为2位小数以内数字")
    private String freight;

    @ApiModelProperty(value = "运费分摊原则", required = false)
    private Integer freightApportionCd;

    @ApiModelProperty(value = "备注", required = false)
    private String remark;

    @ApiModelProperty(value = "验货单产品", required = true)
    private Object storeSkuGroup;

    @ApiModelProperty(value = "到货仓库", required = true)
    private String arrivalWarehouseId;

    @ApiModelProperty(value = "包材仓库", required = false)
    private String packWarehouseId;

}
