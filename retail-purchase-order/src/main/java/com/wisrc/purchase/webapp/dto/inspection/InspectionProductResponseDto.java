package com.wisrc.purchase.webapp.dto.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "到货保存表单")
public class InspectionProductResponseDto extends InspectionProductExcelResponseDto {
    @ApiModelProperty(value = "到货产品ID")
    private String arrivalProductId;
}
