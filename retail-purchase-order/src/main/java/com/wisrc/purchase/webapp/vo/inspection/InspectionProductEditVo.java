package com.wisrc.purchase.webapp.vo.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "到货单产品")
public class InspectionProductEditVo extends InspectionProductVo {
    @ApiModelProperty(value = "到货单产品Id", required = true)
    private String arrivalProductId;
}
