package com.wisrc.sys.webapp.vo.employee;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class SetEmployeeVO {
    @NotBlank(message = "员工编码employeeId不能为空")
    @ApiModelProperty(value = "员工编码", required = true)
    private String employeeId;

    @NotBlank(message = "员工名employeeName不能为空")
    @ApiModelProperty(value = "员工名", required = true)
    private String employeeName;

    @NotNull(message = "状态statusCd不能为空")
    @ApiModelProperty(value = "状态", required = true)
    private Integer statusCd;

    @NotBlank(message = "岗位编码positionCd不能为空")
    @ApiModelProperty(value = "岗位编码", required = true)
    private String positionCd;
}
