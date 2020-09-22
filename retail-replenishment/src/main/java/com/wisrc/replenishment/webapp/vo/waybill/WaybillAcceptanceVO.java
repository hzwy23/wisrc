package com.wisrc.replenishment.webapp.vo.waybill;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;
import java.util.List;

@Api(tags = "确定签收")
public class WaybillAcceptanceVO {
    @ApiModelProperty(value = "运单ID")
    private String waybillId;
    @ApiModelProperty(value = "签收日期")
    private Date signInDate;
    @ApiModelProperty(value = "签收数量集合")
    private List<WaybillMskuNumVO> list;

    public String getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(String waybillId) {
        this.waybillId = waybillId;
    }

    public Date getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(Date signInDate) {
        this.signInDate = signInDate;
    }

    public List<WaybillMskuNumVO> getList() {
        return list;
    }

    public void setList(List<WaybillMskuNumVO> list) {
        this.list = list;
    }
}
