package com.wisrc.sys.webapp.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@ApiModel(description = "用户")
public class AccountSaveVo extends AccountVo {
    @ApiModelProperty(value = "用户", required = true)
    @NotEmpty
    private String userId;
}
