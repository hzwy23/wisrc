package com.wisrc.purchase.webapp.vo.inspection;

import com.wisrc.purchase.webapp.vo.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "人员选择框")
public class EmployeeSelectorVo extends PageVo {
    @ApiModelProperty(value = "人员名称", required = false)
    private String employeeName;
}
