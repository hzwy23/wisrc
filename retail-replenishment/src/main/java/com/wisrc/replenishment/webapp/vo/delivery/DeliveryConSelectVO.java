package com.wisrc.replenishment.webapp.vo.delivery;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class DeliveryConSelectVO {

    @ApiModelProperty(value = "运费估算信息")
    private CalFreightVO calFreightVO;
    @ApiModelProperty(value = "补货单信息")
    private List<FbaDeliveryVO> fbaDeliveryVOList;
    @ApiModelProperty(value = "物流商信息")
    private LogisticsInfoVO logisticsInfoVO;

    public CalFreightVO getCalFreightVO() {
        return calFreightVO;
    }

    public void setCalFreightVO(CalFreightVO calFreightVO) {
        this.calFreightVO = calFreightVO;
    }

    public List<FbaDeliveryVO> getFbaDeliveryVOList() {
        return fbaDeliveryVOList;
    }

    public void setFbaDeliveryVOList(List<FbaDeliveryVO> fbaDeliveryVOList) {
        this.fbaDeliveryVOList = fbaDeliveryVOList;
    }

    public LogisticsInfoVO getLogisticsInfoVO() {
        return logisticsInfoVO;
    }

    public void setLogisticsInfoVO(LogisticsInfoVO logisticsInfoVO) {
        this.logisticsInfoVO = logisticsInfoVO;
    }
}
