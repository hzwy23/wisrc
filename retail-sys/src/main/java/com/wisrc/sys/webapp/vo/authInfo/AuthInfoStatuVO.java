package com.wisrc.sys.webapp.vo.authInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AuthInfoStatuVO {
    @ApiModelProperty(value = "授权权限集合", required = true)
    @NotEmpty(message = "authIdList不能为空")
    private List<String> authIdList;

    @NotNull(message = "状态statusCd不能为空")
    @ApiModelProperty(value = "状态", required = true)
    private Integer statusCd;
}
