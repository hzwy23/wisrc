package com.wisrc.quality.webapp.dto.inspection;

import com.wisrc.quality.webapp.dto.PageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "验货保存表单")
public class InspectionPageDto extends PageDto {
    @ApiModelProperty(value = "验货信息")
    private List<InspectionProductResponseDto> inspectionProductList;
}
