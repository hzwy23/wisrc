package com.wisrc.rules.webapp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "页面返回插件")
public abstract class PageDto {
    @ApiModelProperty(value = "总页数")
    private long total;

    @ApiModelProperty(value = "总数据量")
    private Integer pages;
}
