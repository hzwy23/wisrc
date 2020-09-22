package com.wisrc.replenishment.webapp.vo.wms;


import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 装箱数据回写VO
 */
public class FbaPackingDataReturnVO {
    @ApiModelProperty("补货订单编号")
    private String fbaReplenishmentId;
    @ApiModelProperty("补货商品清单")
    private List<FbaSkuInfoVO> skuEntityList;
    @ApiModelProperty("补货商品的总重量")
    private double totalWeight;
    @ApiModelProperty("补货商品的总数量")
    private double totalQuantity;

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getFbaReplenishmentId() {
        return fbaReplenishmentId;
    }

    public void setFbaReplenishmentId(String fbaReplenishmentId) {
        this.fbaReplenishmentId = fbaReplenishmentId;
    }

    public List<FbaSkuInfoVO> getSkuEntityList() {
        return skuEntityList;
    }

    public void setSkuEntityList(List<FbaSkuInfoVO> skuEntityList) {
        this.skuEntityList = skuEntityList;
    }
}
