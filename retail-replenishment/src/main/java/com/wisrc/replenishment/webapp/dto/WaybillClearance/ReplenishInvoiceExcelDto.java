package com.wisrc.replenishment.webapp.dto.WaybillClearance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReplenishInvoiceExcelDto {
    @ApiModelProperty(value = "项号")
    private Integer num;
    @ApiModelProperty(value = "货物名称、型号规格")
    private String declarationElements;
    @ApiModelProperty(value = "数量")
    private Integer customsCount;
    @ApiModelProperty(value = "单位")
    private String mskuUnitName;
    @ApiModelProperty(value = "单  价")
    private Double declareUnitPrice;
    @ApiModelProperty(value = "总金额  ")
    private Double declareSubtotal;
}
