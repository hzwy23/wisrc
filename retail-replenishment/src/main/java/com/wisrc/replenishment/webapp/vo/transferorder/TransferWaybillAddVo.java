package com.wisrc.replenishment.webapp.vo.transferorder;

import com.wisrc.replenishment.webapp.entity.FreightEstimateinfoEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class TransferWaybillAddVo {
    @ApiModelProperty(value = "运单下单时间")
    private String waybillOrderDate;
    @ApiModelProperty(value = "备注信息")
    private String remark;
    @ApiModelProperty(value = "预计签收日期")
    private String estimateDate;
    @ApiModelProperty(value = "物流报价单")
    private String offerId;
    @ApiModelProperty(value = "调拨单号")
    @NotNull
    private String transferOrderCd;
    @ApiModelProperty(value = "运费估算信息", position = 6)
    private FreightEstimateinfoEntity freightEstimateinfoEntity;

    public String getTransferOrderCd() {
        return transferOrderCd;
    }

    public void setTransferOrderCd(String transferOrderCd) {
        this.transferOrderCd = transferOrderCd;
    }

    public String getWaybillOrderDate() {
        return waybillOrderDate;
    }

    public void setWaybillOrderDate(String waybillOrderDate) {
        this.waybillOrderDate = waybillOrderDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(String estimateDate) {
        this.estimateDate = estimateDate;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public FreightEstimateinfoEntity getFreightEstimateinfoEntity() {
        return freightEstimateinfoEntity;
    }

    public void setFreightEstimateinfoEntity(FreightEstimateinfoEntity freightEstimateinfoEntity) {
        this.freightEstimateinfoEntity = freightEstimateinfoEntity;
    }
}
