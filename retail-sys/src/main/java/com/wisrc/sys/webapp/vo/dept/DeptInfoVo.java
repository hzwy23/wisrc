package com.wisrc.sys.webapp.vo.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(description = "部门")
public class DeptInfoVo {
    @ApiModelProperty(value = "部门名称", required = true)
    @NotEmpty(message = "部门名称不能为空")
    private String deptName;

    @ApiModelProperty(value = "上级部门", required = false)
    private String parentDept;

    private Integer deptTypeAttr;
}
