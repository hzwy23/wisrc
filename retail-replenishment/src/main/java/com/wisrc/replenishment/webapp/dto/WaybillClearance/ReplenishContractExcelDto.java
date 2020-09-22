package com.wisrc.replenishment.webapp.dto.WaybillClearance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReplenishContractExcelDto {
    @ApiModelProperty(value = "项号")
    private Integer num;
    @ApiModelProperty(value = "货物名称及规格")
    private String declarationElements;
    @ApiModelProperty(value = "数 量")
    private Integer customsCount;
    @ApiModelProperty(value = "单 位")
    private String mskuUnitName;
    @ApiModelProperty(value = "单价")
    private Double declareUnitPrice;
    @ApiModelProperty(value = "金  额")
    private Double declareSubtotal;
    @ApiModelProperty(value = "外汇币制")
    private String moneyType;
}
