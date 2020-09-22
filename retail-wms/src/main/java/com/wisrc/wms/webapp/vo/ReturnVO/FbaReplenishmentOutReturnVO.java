package com.wisrc.wms.webapp.vo.ReturnVO;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class FbaReplenishmentOutReturnVO {
    @ApiModelProperty("补货单号")
    private String fbaReplenishmentId;
    @ApiModelProperty("总箱数")
    private Integer numberOfBoxes;
    @ApiModelProperty("总数量")
    private Integer replenishmentQuantity;

    private List<FbaOutSkuVO> skuList;

    public String getFbaReplenishmentId() {
        return fbaReplenishmentId;
    }

    public void setFbaReplenishmentId(String fbaReplenishmentId) {
        this.fbaReplenishmentId = fbaReplenishmentId;
    }

    public Integer getNumberOfBoxes() {
        return numberOfBoxes;
    }

    public void setNumberOfBoxes(Integer numberOfBoxes) {
        this.numberOfBoxes = numberOfBoxes;
    }

    public Integer getReplenishmentQuantity() {
        return replenishmentQuantity;
    }

    public void setReplenishmentQuantity(Integer replenishmentQuantity) {
        this.replenishmentQuantity = replenishmentQuantity;
    }

    public List<FbaOutSkuVO> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<FbaOutSkuVO> skuList) {
        this.skuList = skuList;
    }
}
