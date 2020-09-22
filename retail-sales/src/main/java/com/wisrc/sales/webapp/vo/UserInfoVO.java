package com.wisrc.sales.webapp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * UserInfoVO
 * 用户信息vo
 *
 * @author MAJANNING
 * @date 2018/7/12
 */
@Data
@AllArgsConstructor
public class UserInfoVO {
    private String userId;
    private String userName;
    private String employeeId;
    private String employeeName;
    //岗位编号
    private String positionCd;
}
