package com.wisrc.replenishment.webapp.vo.delivery;

import com.wisrc.replenishment.webapp.entity.FreightEstimateinfoEntity;

/**
 * 确认交运VO
 */
public class DeliveryConfirmVO {

    private LogisticsInfoVO logisticsInfo;

    private FreightEstimateinfoEntity freightEstimateinfoEntity;

    public LogisticsInfoVO getLogisticsInfo() {
        return logisticsInfo;
    }

    public void setLogisticsInfo(LogisticsInfoVO logisticsInfo) {
        this.logisticsInfo = logisticsInfo;
    }

    public FreightEstimateinfoEntity getFreightEstimateinfoEntity() {
        return freightEstimateinfoEntity;
    }

    public void setFreightEstimateinfoEntity(FreightEstimateinfoEntity freightEstimateinfoEntity) {
        this.freightEstimateinfoEntity = freightEstimateinfoEntity;
    }
}
