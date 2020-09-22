package com.wisrc.replenishment.webapp.vo.waybill;

import com.wisrc.replenishment.webapp.entity.WaybillExceptionInfoEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@Api(tags = "新增标记异常")
public class WaybillExceptionVO {
    @ApiModelProperty(value = "运单号")
    private String waybillId;
    @ApiModelProperty(value = "标记异常集合")

    private List<WaybillExceptionInfoEntity> waybillExceptionInfoEntityList;

    public String getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(String waybillId) {
        this.waybillId = waybillId;
    }

    public List<WaybillExceptionInfoEntity> getWaybillExceptionInfoEntityList() {
        return waybillExceptionInfoEntityList;
    }

    public void setWaybillExceptionInfoEntityList(List<WaybillExceptionInfoEntity> waybillExceptionInfoEntityList) {
        this.waybillExceptionInfoEntityList = waybillExceptionInfoEntityList;
    }
}
