package com.wisrc.sys.webapp.vo.own;

import lombok.Data;

@Data
public class GetRoleInfoVO {
    private String roleId;
    private String roleName;
    private String accountId;//账号id
    private Integer statusCd;
}
