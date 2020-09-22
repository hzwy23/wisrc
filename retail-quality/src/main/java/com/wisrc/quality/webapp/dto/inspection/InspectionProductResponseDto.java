package com.wisrc.quality.webapp.dto.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "验货保存表单")
public class InspectionProductResponseDto extends InspectionProductExcelResponseDto {
    @ApiModelProperty(value = "产品验货ID")
    private String inspectionProductId;
}
