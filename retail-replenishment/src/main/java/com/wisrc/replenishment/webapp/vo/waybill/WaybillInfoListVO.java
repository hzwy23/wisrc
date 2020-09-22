package com.wisrc.replenishment.webapp.vo.waybill;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
@Api(tags = "跟踪单基本信息列表和异常信息条数")
public class WaybillInfoListVO {
    @ApiModelProperty(value = "运单号缺少相关信息")
    private WaybillIfInfoVO waybillIfInfoVO;
    @ApiModelProperty(value = "跟踪单基本信息列表")
    private LinkedHashMap waybillInfoVOList;

    public WaybillIfInfoVO getWaybillIfInfoVO() {
        return waybillIfInfoVO;
    }

    public void setWaybillIfInfoVO(WaybillIfInfoVO waybillIfInfoVO) {
        this.waybillIfInfoVO = waybillIfInfoVO;
    }

    public LinkedHashMap getWaybillInfoVOList() {
        return waybillInfoVOList;
    }

    public void setWaybillInfoVOList(LinkedHashMap waybillInfoVOList) {
        this.waybillInfoVOList = waybillInfoVOList;
    }
}
