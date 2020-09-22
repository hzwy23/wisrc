package com.wisrc.merchandise.vo.outside;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MskuRelationVo {
    @ApiModelProperty(value = "店铺安全钥匙")
    @NotEmpty(message = "awsAccessKey不能为空")
    private String shopOwnerId;
    @ApiModelProperty(value = "msku编号")
    @NotEmpty(message = "msku不能为空")
    private String msku;
    @ApiModelProperty(value = "组合id")
    @NotEmpty(message = "uniqueCode不能为空")
    private String uniqueCode;
}
