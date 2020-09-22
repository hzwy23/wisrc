package com.wisrc.replenishment.webapp.vo.waybill;

import com.wisrc.replenishment.webapp.entity.LogisticsTrackInfoEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LogisticsTrackInfoVO {

    @ApiModelProperty(value = "物流跟踪信息 时间和说明")
    private List<LogisticsTrackInfoEntity> logisticsInfoList;
    @ApiModelProperty(value = "物流渠道")
    private String shipChannel;
    @ApiModelProperty(value = "物流单号")
    private String logisticsId;
    @ApiModelProperty(value = "预计签收日期", hidden = true)
    private Date estimateDate;
    @ApiModelProperty(value = "签收日期", hidden = true)
    private Date signInDate;
    @ApiModelProperty(value = "异常说明")
    private String ExceptionDesc;
    @ApiModelProperty(value = "货运公司代号编号")
    private Integer codeCd;

    public List<LogisticsTrackInfoEntity> getLogisticsInfoList() {
        return logisticsInfoList;
    }

    public void setLogisticsInfoList(List<LogisticsTrackInfoEntity> logisticsInfoList) {
        this.logisticsInfoList = logisticsInfoList;
    }

    public String getShipChannel() {
        return shipChannel;
    }

    public void setShipChannel(String shipChannel) {
        this.shipChannel = shipChannel;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public Date getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(Date signInDate) {
        this.signInDate = signInDate;
    }

    public String getExceptionDesc() {
        return ExceptionDesc;
    }

    public void setExceptionDesc(String exceptionDesc) {
        ExceptionDesc = exceptionDesc;
    }
}
