package com.wisrc.purchase.webapp.dto.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "验货保存表单")
public class InspectionExcelDto {
    @ApiModelProperty(value = "验货信息")
    private List<InspectionProductExcelResponseDto> inspectionProductList;
}
