package com.wisrc.sys.webapp.vo.swagger;

import com.wisrc.sys.webapp.entity.VUserCategoryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "请求返回对象结构信息")
public class VUserCategoryVO {
    @ApiModelProperty(value = "请求返回码", position = 1)
    private int code;
    @ApiModelProperty(value = "请求返回消息", position = 2)
    private String msg;
    @ApiModelProperty(value = "返回数据", position = 3)
    private List<VUserCategoryEntity> data;
}
