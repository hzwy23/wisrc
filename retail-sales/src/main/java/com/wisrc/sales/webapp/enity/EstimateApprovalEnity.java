package com.wisrc.sales.webapp.enity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class EstimateApprovalEnity {
    //主键
    private String uuid;
    //外键
    private String estimateId;
    // 主管审批状态
    private String directApprovStatus;
    // 经理审批状态
    private String managerApprovStatus;
    // 计划部审批状态
    private String planDepartApprovStatus;
    // 主管备注
    private String directApprovRemark;
    // 经理备注
    private String managerApprovRemark;
    // 计划部备注
    private String planDepartApprovRemark;
    // 生效日期
    private Timestamp effectiveDate;
    // 时效日期
    private Timestamp expirationDate;

    @ApiModelProperty(value = "更新标识", required = false, hidden = true)
    private Integer updateFlag;
}
