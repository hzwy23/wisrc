package com.wisrc.purchase.webapp.vo.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel
public class ArrivalProductEditVo {
    @ApiModelProperty(value = "到货产品Id")
    @NotEmpty(message = "")
    private String arrivalProductId;

    @ApiModelProperty(value = "验货数量")
    private Integer inspectionQuantity;

    @ApiModelProperty(value = "合格数")
    private Integer qualifiedQualified;

    @ApiModelProperty(value = "不合格数")
    private Integer unqualifiedQuantity;
}
