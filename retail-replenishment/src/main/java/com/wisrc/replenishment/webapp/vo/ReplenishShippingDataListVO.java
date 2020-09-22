package com.wisrc.replenishment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ReplenishShippingDataListVO {
    private String replenishmentCommodityId;
    @ApiModelProperty("skuId,调拨单发货专用字段")
    private String skuId;
    private List<ReplenishShippingDataVO> list;
    private List<OverseaSendVO> sendVOList;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getReplenishmentCommodityId() {
        return replenishmentCommodityId;
    }

    public void setReplenishmentCommodityId(String replenishmentCommodityId) {
        this.replenishmentCommodityId = replenishmentCommodityId;
    }

    public List<ReplenishShippingDataVO> getList() {
        return list;
    }

    public void setList(List<ReplenishShippingDataVO> list) {
        this.list = list;
    }

    public List<OverseaSendVO> getSendVOList() {
        return sendVOList;
    }

    public void setSendVOList(List<OverseaSendVO> sendVOList) {
        this.sendVOList = sendVOList;
    }
}
