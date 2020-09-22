package com.wisrc.basic.vo.swagger;

import com.wisrc.basic.entity.BasicShopInfoEntity;
import io.swagger.annotations.ApiModelProperty;

public class ShopInfoResponseModelDetails {

    @ApiModelProperty(value = "返回状态吗", position = 1)
    private int code;

    @ApiModelProperty(value = "返回消息", position = 2)
    private int msg;

    @ApiModelProperty(value = "店铺详细信息", position = 3)
    private BasicShopInfoEntity data;

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

    public BasicShopInfoEntity getData() {
        return data;
    }

    public void setData(BasicShopInfoEntity data) {
        this.data = data;
    }
}