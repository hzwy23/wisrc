package com.wisrc.sys.webapp.vo.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class SetAccountVO {
    @NotBlank(message = "员工编码employeeId不能为空")
    @ApiModelProperty(value = "账户ID", required = true)
    private String userId;

    @NotBlank(message = "账户名userName不能为空")
    @ApiModelProperty(value = "账户名", required = true)
    private String userName;

    @ApiModelProperty(value = "状态", required = true)
    private Integer statusCd;

    @ApiModelProperty(value = "创建人", required = false)
    private String createUser;


    @ApiModelProperty(value = "手机号码", required = false)
    private String phoneNumber;

    @ApiModelProperty(value = "QQ", required = false)
    private Integer qq;

    @ApiModelProperty(value = "微信", required = false)
    private String weixin;

    @ApiModelProperty(value = "电子邮箱", required = false)
    private String email;

    @ApiModelProperty(value = "座机号码", required = false)
    private String telephoneNumber;

    @NotBlank(message = "员工编码employeeId不能为空")
    @ApiModelProperty(value = "员工编码", required = true)
    private String employeeId;

    @ApiModelProperty(value = "worktileId", required = false)
    private String worktileId;

}
