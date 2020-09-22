package com.wisrc.purchase.webapp.dto.inspection;

import com.wisrc.purchase.webapp.dto.PageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "到货保存表单")
public class InspectionPageDto extends PageDto {
    @ApiModelProperty(value = "到货信息")
    private List<InspectionProductResponseDto> inspectionProductList;
}
