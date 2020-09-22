package com.wisrc.wms.webapp.vo.ReturnVO;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TransferOrderPackBasicVO {

    @ApiModelProperty(value = "调拨单号")
    private String transferOrderId;

    @ApiModelProperty(value = "总重量")
    private int totalWeight;

    @ApiModelProperty(value = "总数量")
    private int totalQuantity;

    List<TransferOrderPackProductVO> skuEntityList;

    public String getTransferOrderId() {
        return transferOrderId;
    }

    public void setTransferOrderId(String transferOrderId) {
        this.transferOrderId = transferOrderId;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public List<TransferOrderPackProductVO> getSkuEntityList() {
        return skuEntityList;
    }

    public void setSkuEntityList(List<TransferOrderPackProductVO> skuEntityList) {
        this.skuEntityList = skuEntityList;
    }
}