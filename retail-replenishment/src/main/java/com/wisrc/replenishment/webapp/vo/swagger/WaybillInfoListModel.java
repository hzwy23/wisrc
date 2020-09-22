package com.wisrc.replenishment.webapp.vo.swagger;

import com.wisrc.replenishment.webapp.vo.waybill.WaybillIfInfoVO;
import com.wisrc.replenishment.webapp.vo.waybill.WaybillInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@Api(tags = "跟踪单基本信息列表和异常信息条数")
public class WaybillInfoListModel {
    @ApiModelProperty(value = "运单号缺少相关信息")
    private WaybillIfInfoVO waybillIfInfoVO;
    @ApiModelProperty(value = "跟踪单基本信息列表")
    private List<WaybillInfoVO> waybillInfoVOList;

    public WaybillIfInfoVO getWaybillIfInfoVO() {
        return waybillIfInfoVO;
    }

    public void setWaybillIfInfoVO(WaybillIfInfoVO waybillIfInfoVO) {
        this.waybillIfInfoVO = waybillIfInfoVO;
    }

    public List<WaybillInfoVO> getWaybillInfoVOList() {
        return waybillInfoVOList;
    }

    public void setWaybillInfoVOList(List<WaybillInfoVO> waybillInfoVOList) {
        this.waybillInfoVOList = waybillInfoVOList;
    }
}
