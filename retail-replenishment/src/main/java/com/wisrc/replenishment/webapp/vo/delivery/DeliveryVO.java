package com.wisrc.replenishment.webapp.vo.delivery;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 交运信息
 */
public class DeliveryVO {

    @ApiModelProperty(value = "补货单信息")
    private List<FbaDeliveryVO> fbaDeliveryVOList;

    @ApiModelProperty(value = "计费估算信息")
    private List<BillEstimateVO> billEstimateVOList;


    public List<FbaDeliveryVO> getFbaDeliveryVOList() {
        return fbaDeliveryVOList;
    }

    public void setFbaDeliveryVOList(List<FbaDeliveryVO> fbaDeliveryVOList) {
        this.fbaDeliveryVOList = fbaDeliveryVOList;
    }

    public List<BillEstimateVO> getBillEstimateVOList() {
        return billEstimateVOList;
    }

    public void setBillEstimateVOList(List<BillEstimateVO> billEstimateVOList) {
        this.billEstimateVOList = billEstimateVOList;
    }

}
