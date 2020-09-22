package com.wisrc.code.webapp.vo.codeTemplateConf;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "上传文件信息保存")
public class CodeTemplateConfSaveVo {
    @ApiModelProperty(value = "文件名称")
    private String itemName;

    @ApiModelProperty(value = "文件名称")
    private String obsName;

    @ApiModelProperty(value = "文件地址")
    private String addr;

    @ApiModelProperty(value = "版本号")
    private Integer version;
}
