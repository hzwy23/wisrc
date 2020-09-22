package com.wisrc.sys.webapp.vo.position;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(description = "岗位")
public class PositionInfoVo {
    @ApiModelProperty(value = "岗位名称", required = true)
    @NotEmpty
    private String name;

    @ApiModelProperty(value = "上级岗位", required = false)
    private String parent;

    @ApiModelProperty(value = "部门编号", required = true)
    @NotEmpty
    private String department;

    private Integer executiveDirectorAttr;
}
