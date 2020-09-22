package com.wisrc.sys.webapp.vo.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

@ApiModel(description = "部门")
public class DeptInfoSaveVo extends DeptInfoVo {
    @ApiModelProperty(value = "部门Id", required = true)
    @NotEmpty(message = "部门id不能为空")
    private String deptId;

    @ApiModelProperty(value = "部门属性")
    private Integer deptTypeAttr;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Override
    public Integer getDeptTypeAttr() {
        return deptTypeAttr;
    }

    @Override
    public void setDeptTypeAttr(Integer deptTypeAttr) {
        if (deptTypeAttr == null) {
            this.deptTypeAttr = 0;
        } else {
            this.deptTypeAttr = deptTypeAttr;
        }
    }


    @Override
    public String toString() {
        return "DeptInfoSaveVo{" +
                "deptId='" + deptId + '\'' +
                ", deptTypeAttr=" + deptTypeAttr +
                '}';
    }
}
