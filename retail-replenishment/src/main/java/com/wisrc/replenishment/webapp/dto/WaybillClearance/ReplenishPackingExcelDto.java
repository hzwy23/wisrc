package com.wisrc.replenishment.webapp.dto.WaybillClearance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReplenishPackingExcelDto {
    @ApiModelProperty(value = "项号")
    private Integer num;
    @ApiModelProperty(value = "货物名称及规格")
    private String declarationElements;
    @ApiModelProperty(value = "总数(件)")
    private Integer numberOfBoxes;
    @ApiModelProperty(value = "总数量")
    private Integer customsCount;
    @ApiModelProperty(value = "总毛重(千克)")
    private Double grossWeight;
    @ApiModelProperty(value = "总净重(千克)")
    private Double netWeight;
}
