package com.wisrc.sys.webapp.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel(description = "用户")
public class AccountVo {
    @ApiModelProperty(value = "名称", required = true)
    @NotEmpty
    private String userName;

    @ApiModelProperty(value = "所属人员", required = true)
    @NotEmpty
    private String employeeId;

    @ApiModelProperty(value = "密码", required = true)
    @NotEmpty
    private String password;

    @ApiModelProperty(value = "所属角色", required = true)
    private List<String> roleIds;
}
