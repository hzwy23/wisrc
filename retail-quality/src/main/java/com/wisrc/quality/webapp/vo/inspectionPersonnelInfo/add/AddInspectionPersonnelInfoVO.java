package com.wisrc.quality.webapp.vo.inspectionPersonnelInfo.add;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class AddInspectionPersonnelInfoVO {
    @Valid
    @NotNull(message = "人员ID[人员ID]不能为空")
    @ApiModelProperty(value = "人员ID", required = true)
    private String employeeId;
}
