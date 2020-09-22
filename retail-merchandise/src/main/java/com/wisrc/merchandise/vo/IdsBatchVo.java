package com.wisrc.merchandise.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "批量传输商品id")
public class IdsBatchVo {
    @ApiModelProperty(value = "商品id数组", required = true)
    private List<String> ids;

    @ApiModelProperty(value = "负责人", required = true)
    private String manager;
}
