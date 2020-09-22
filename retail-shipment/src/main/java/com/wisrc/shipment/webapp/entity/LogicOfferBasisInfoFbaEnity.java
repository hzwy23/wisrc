package com.wisrc.shipment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class LogicOfferBasisInfoFbaEnity {
    @ApiModelProperty(value = "返回状态吗", position = 1, hidden = true)
    private int code;
    @ApiModelProperty(value = "返回消息", position = 2, hidden = true)
    private int msg;
    @ApiModelProperty(value = "报价ID")
    private String offerId;
    @ApiModelProperty(value = "物流商ID", required = true)
    @NotEmpty
    private String shipmentId;
    @NotEmpty
    private String channelName;
    @ApiModelProperty(value = "渠道类型ID，1--空运，2--海运", required = true)
    @NotNull(message = "channelTypeCd不能为空")
    private int channelTypeCd;
    @ApiModelProperty(value = "计费类型ID", required = true)
    @NotNull(message = "chargeTypeCd不能为空")
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
    private int deleteStatus;
    @Min(value = 0, message = "报关费必须为正数")
    @ApiModelProperty(value = "报关费")
    private Double customsDeclarationFee;
    @ApiModelProperty(value = "过港费")
    @Min(value = 0, message = "过港费必须为正数")
    private Double portFee;
    @ApiModelProperty(value = "标计价类型描述", hidden = true)
    private String chargeTypeName;
    @NotNull(message = "offerTypeCd不能为空")
    @ApiModelProperty(value = "报价类型ID", required = true)
    private Integer offerTypeCd;
    @Valid
    @NotEmpty
    private List<LogisticsOfferEffectiveEntity> effectiveList;
    @Valid
    @NotEmpty
    private List<LogisticsOfferHistoryInfoEntity> historyList;
    private List<LogisticsAllowLabelRelEntity> labelList;
    @Valid
    @NotEmpty
    private List<LogisticsOfferDestinationEnity> destinationList;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

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

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Double getCustomsDeclarationFee() {
        return customsDeclarationFee;
    }

    public void setCustomsDeclarationFee(Double customsDeclarationFee) {
        this.customsDeclarationFee = customsDeclarationFee;
    }

    public Double getPortFee() {
        return portFee;
    }

    public void setPortFee(Double portFee) {
        this.portFee = portFee;
    }

    public String getChargeTypeName() {
        return chargeTypeName;
    }

    public void setChargeTypeName(String chargeTypeName) {
        this.chargeTypeName = chargeTypeName;
    }

    public Integer getOfferTypeCd() {
        return offerTypeCd;
    }

    public void setOfferTypeCd(Integer offerTypeCd) {
        this.offerTypeCd = offerTypeCd;
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

    public List<LogisticsOfferDestinationEnity> getDestinationList() {
        return destinationList;
    }

    public void setDestinationList(List<LogisticsOfferDestinationEnity> destinationList) {
        this.destinationList = destinationList;
    }
}
