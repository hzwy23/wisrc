package com.wisrc.replenishment.webapp.vo.waybill;

import com.wisrc.replenishment.webapp.vo.FbaMskuInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@Api(tags = "商品信息列表")
public class ListFbaMsKuInfoVO {
    @ApiModelProperty(value = "商品信息列表")
    private List<FbaMskuInfoVO> FbaList;

    public List<FbaMskuInfoVO> getFbaList() {
        return FbaList;
    }

    public void setFbaList(List<FbaMskuInfoVO> fbaList) {
        FbaList = fbaList;
    }

    @Override
    public String toString() {
        return "ListFbaMsKuInfoVO{" +
                "FbaList=" + FbaList +
                '}';
    }
}
