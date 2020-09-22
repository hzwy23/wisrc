package com.wisrc.shipment.webapp.vo.swagger;

import com.wisrc.shipment.webapp.entity.LogisticsOfferHistoryInfoEntity;
import com.wisrc.shipment.webapp.entity.LogisticsAllowLabelRelEntity;
import com.wisrc.shipment.webapp.entity.LogisticsOfferEffectiveEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class LogisticsOfferBasisInfoModel {

    @ApiModelProperty(value = "报价ID", hidden = true)
    private String offerId;
    @ApiModelProperty(value = "物流商ID", required = true)
    private String shipmentId;
    @ApiModelProperty(value = "渠道名称", required = true)
    private String channelName;
    @ApiModelProperty(value = "渠道类型ID,1--空运，2--海运", required = true)
    private int channelTypeCd;
    @ApiModelProperty(value = "计费类型ID", required = true)
    private int chargeTypeCd;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "物流跟踪URL")
    private String logisticsTrackUrl;
    @ApiModelProperty(value = "更新时间", hidden = true)
    private String modifyTime;
    @ApiModelProperty(value = "更新人", hidden = true)
    private String modifyUser;
    @ApiModelProperty(value = "状态，0--正常，1--已删除")
    private int status;
    private List<LogisticsOfferEffectiveEntity> effectiveList;
    private List<LogisticsOfferHistoryInfoEntity> historyList;
    private List<LogisticsAllowLabelRelEntity> labelList;

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getChannelTypeCd() {
        return channelTypeCd;
    }

    public void setChannelTypeCd(int channelTypeCd) {
        this.channelTypeCd = channelTypeCd;
    }

    public int getChargeTypeCd() {
        return chargeTypeCd;
    }

    public void setChargeTypeCd(int chargeTypeCd) {
        this.chargeTypeCd = chargeTypeCd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLogisticsTrackUrl() {
        return logisticsTrackUrl;
    }

    public void setLogisticsTrackUrl(String logisticsTrackUrl) {
        this.logisticsTrackUrl = logisticsTrackUrl;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<LogisticsOfferEffectiveEntity> getEffectiveList() {
        return effectiveList;
    }

    public void setEffectiveList(List<LogisticsOfferEffectiveEntity> effectiveList) {
        this.effectiveList = effectiveList;
    }

    public List<LogisticsOfferHistoryInfoEntity> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<LogisticsOfferHistoryInfoEntity> historyList) {
        this.historyList = historyList;
    }

    public List<LogisticsAllowLabelRelEntity> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<LogisticsAllowLabelRelEntity> labelList) {
        this.labelList = labelList;
    }
}
