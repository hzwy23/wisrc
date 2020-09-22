package com.wisrc.sys.webapp.vo.position;

import com.wisrc.sys.webapp.vo.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "岗位页面")
public class PositionPageVo extends PageVo {
    @ApiModelProperty(value = "岗位编号", required = false)
    private String jobId;

    @ApiModelProperty(value = "岗位名称", required = false)
    private String jobName;

    @ApiModelProperty(value = "部门菜单编号", required = false)
    private String value;


    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
