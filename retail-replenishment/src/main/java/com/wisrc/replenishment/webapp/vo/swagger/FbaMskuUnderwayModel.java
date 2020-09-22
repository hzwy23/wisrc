package com.wisrc.replenishment.webapp.vo.swagger;

import com.wisrc.replenishment.webapp.entity.VReplenishmentMskuEntity;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel
public class FbaMskuUnderwayModel {
    private int code;
    private String msg;
    private List<VReplenishmentMskuEntity> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<VReplenishmentMskuEntity> getData() {
        return data;
    }

    public void setData(List<VReplenishmentMskuEntity> data) {
        this.data = data;
    }
}
