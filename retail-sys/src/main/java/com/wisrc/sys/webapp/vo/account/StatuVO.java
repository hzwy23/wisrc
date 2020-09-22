package com.wisrc.sys.webapp.vo.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel
public class StatuVO {
    @NotEmpty(message = "员工编码集合userIdList不能为空")
    @ApiModelProperty(value = "员工编码集合", required = true)
    private List<String> userIdList;

//    @Range(min = 1, max = 2, message = "无效的参数[statusCd]")
    @ApiModelProperty(value = "状态编码（1，启用。2，禁用） ", required = true)
    private Integer statusCd;
}
