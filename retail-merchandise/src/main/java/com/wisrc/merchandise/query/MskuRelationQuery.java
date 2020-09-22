package com.wisrc.merchandise.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MskuRelationQuery {
    @ApiModelProperty(value = "店铺安全钥匙")
    @NotEmpty
    private String shopOwnerId;
    @ApiModelProperty(value = "msku编号")
    @NotEmpty
    private String msku;
}
