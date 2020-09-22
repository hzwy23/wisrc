package com.wisrc.sys.webapp.vo.employee;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class EmployeeParaVO {
    @ApiModelProperty(value = "员工编码", required = false)
    private String employeeId;
    @ApiModelProperty(value = "员工名", required = false)
    private String employeeName;
    @ApiModelProperty(value = "岗位编码", required = false)
    private String positionCd;
    @ApiModelProperty(value = "岗位编码", required = false)
    private String positionName;
    @ApiModelProperty(value = "部门编码", required = false)
    private String deptCd;
    @ApiModelProperty(value = "部门名称", required = false)
    private String deptName;
    @ApiModelProperty(value = "人员状态", required = false)
    private Integer statusCd;
}
