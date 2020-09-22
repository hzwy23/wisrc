package com.wisrc.replenishment.webapp.vo.swagger;

import com.wisrc.replenishment.webapp.entity.FbaReplenishmentLabelAttrEntity;
import io.swagger.annotations.ApiModelProperty;

public class FbaReplenishmentLabelAttrModel {

    @ApiModelProperty(value = "返回状态吗", position = 1)
    private int code;

    @ApiModelProperty(value = "返回消息", position = 2)
    private int msg;

    @ApiModelProperty(value = "标签详细信息", position = 3)
    private FbaReplenishmentLabelAttrEntity data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

    public FbaReplenishmentLabelAttrEntity getData() {
        return data;
    }

    public void setData(FbaReplenishmentLabelAttrEntity data) {
        this.data = data;
    }
}
