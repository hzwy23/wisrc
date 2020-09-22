package com.wisrc.sys.webapp.vo.account;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class AccountVO {
    private String userId;// 账号id

    private String userName;// 账号名称

    private Integer statusCd;// 账号状态

    private String organizeId;// 部门id
}
