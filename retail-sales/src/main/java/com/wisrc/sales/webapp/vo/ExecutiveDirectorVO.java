package com.wisrc.sales.webapp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ExecutiveDirectorVO
 * 用于记录负责人和类目主管
 *
 * @author MAJANNING
 * @date 2018/7/11
 */
@Data
@AllArgsConstructor
public class ExecutiveDirectorVO {
    private String chargeEmployeeId;
    private String chargeEmployeeName;
    private String executiveDirectorId;
    private String executiveDirectorName;

}
