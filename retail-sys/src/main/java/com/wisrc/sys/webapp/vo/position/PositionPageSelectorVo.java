package com.wisrc.sys.webapp.vo.position;

import com.wisrc.sys.webapp.vo.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "岗位页面")
public class PositionPageSelectorVo extends PageVo {
    @ApiModelProperty(value = "当前所属部门", required = false)
    private String positionNow;

    @ApiModelProperty(value = "部门名称/编号", required = false)
    private String deptFindKey;

    @ApiModelProperty(value = "岗位名称/编号", required = false)
    private String jobFindKey;

    public String getPositionNow() {
        return positionNow;
    }

    public void setPositionNow(String positionNow) {
        this.positionNow = positionNow;
    }
}
