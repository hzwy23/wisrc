package com.wisrc.replenishment.webapp.vo.transferorder;

import com.wisrc.replenishment.webapp.entity.TransferOrderPackInfoEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class PackChangeInfoVo {
    @ApiModelProperty("调拨单号")
    private String transferOrderCd;
    @ApiModelProperty("库存sku")
    private String skuId;
    @ApiModelProperty("对应的装箱规格")
    private List<TransferOrderPackInfoEntity> packInfoEntities;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public List<TransferOrderPackInfoEntity> getPackInfoEntities() {
        return packInfoEntities;
    }

    public void setPackInfoEntities(List<TransferOrderPackInfoEntity> packInfoEntities) {
        this.packInfoEntities = packInfoEntities;
    }

    public String getTransferOrderCd() {
        return transferOrderCd;

    }

    public void setTransferOrderCd(String transferOrderCd) {
        this.transferOrderCd = transferOrderCd;
    }
}

