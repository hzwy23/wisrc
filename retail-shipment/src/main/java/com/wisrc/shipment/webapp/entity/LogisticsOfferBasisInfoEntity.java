package com.wisrc.shipment.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class LogisticsOfferBasisInfoEntity {
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
    private int channelTypeCd;
    @ApiModelProperty(value = "计费类型ID", required = true)
    private int chargeTypeCd;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "物流跟踪URL")
    private String logisticsTrackUrl;
    @ApiModelProperty(value = "更新时间")
    private String modifyTime;
    @ApiModelProperty(value = "更新人")
    private String modifyUser;
    @ApiModelProperty(value = "状态，0--正常，1--已删除")
    private int deleteStatus;
    @ApiModelProperty(value = "报关费")
    private Double customsDeclarationFee;
    @ApiModelProperty(value = "过港费")
    private Double portFee;
    @ApiModelProperty(value = "挂号费")
    private Double register;
    @ApiModelProperty(value = "优惠折扣")
    private Double discount;
    @ApiModelProperty(value = "报价类型, 1--FBA报价，2--小包报价", required = true)
    private int offerTypeCd;
    @ApiModelProperty(value = "标计价类型描述", hidden = true)
    private String chargeTypeName;
    @ApiModelProperty(value = "物流商名称", hidden = true)
    private String shipMentName;
    @NotEmpty
    private List<LogisticsOfferEffectiveEntity> effectiveList;
    private List<LogisticsOfferHistoryInfoEntity> historyList;
    private List<LogisticsAllowLabelRelEntity> labelList;
    @NotEmpty
    private List<LogisticsOfferDestinationEnity> destinationList;
    private List<LittlePackOfferDetailsEntity> littlePacketList;

    public String getShipMentName() {
        return shipMentName;
    }

    public void setShipMentName(String shipMentName) {
        this.shipMentName = shipMentName;
    }

    public String getChargeTypeName() {
        return chargeTypeName;
    }

    public void setChargeTypeName(String chargeTypeName) {
        this.chargeTypeName = chargeTypeName;
    }

    public List<LittlePackOfferDetailsEntity> getLittlePacketList() {
        return littlePacketList;
    }

    public void setLittlePacketList(List<LittlePackOfferDetailsEntity> littlePacketList) {
        this.littlePacketList = littlePacketList;
    }

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

    public Double getRegister() {
        return register;
    }

    public void setRegister(Double register) {
        this.register = register;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public int getOfferTypeCd() {
        return offerTypeCd;
    }

    public void setOfferTypeCd(int offerTypeCd) {
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
