package com.wisrc.replenishment.webapp.vo.waybill;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;
import java.sql.Timestamp;

@Api(tags = "完善物流信息")
public class PerfectWaybillInfoVO {
    @ApiModelProperty(value = "运单编码", required = true)
    private String waybillId;
    @ApiModelProperty(value = "物流单号")
    private String logisticsId;
    @ApiModelProperty(value = "物流面单(附件地址)")
    private String logisticsSurfaceUrl;
    @ApiModelProperty(value = "检测报告(附件地址)")
    private String examiningReportUrl;
    @ApiModelProperty(value = "预计签收日期")
    private Date estimateDate;
    @ApiModelProperty(value = "修改时间", hidden = true)
    private Timestamp modifyTime;
    @ApiModelProperty(value = "修改人", hidden = true)
    private String modifyUser;

    public String getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(String waybillId) {
        this.waybillId = waybillId;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getLogisticsSurfaceUrl() {
        return logisticsSurfaceUrl;
    }

    public void setLogisticsSurfaceUrl(String logisticsSurfaceUrl) {
        this.logisticsSurfaceUrl = logisticsSurfaceUrl;
    }

    public String getExaminingReportUrl() {
        return examiningReportUrl;
    }

    public void setExaminingReportUrl(String examiningReportUrl) {
        this.examiningReportUrl = examiningReportUrl;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }

    public void setEstimateDate(Date estimateDate) {
        this.estimateDate = estimateDate;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    @Override
    public String toString() {
        return "PerfectWaybillInfoVO{" +
                "waybillId='" + waybillId + '\'' +
                ", logisticsId='" + logisticsId + '\'' +
                ", logisticsSurfaceUrl='" + logisticsSurfaceUrl + '\'' +
                ", examiningReportUrl='" + examiningReportUrl + '\'' +
                ", estimateDate=" + estimateDate +
                ", modifyTime=" + modifyTime +
                ", modifyUser='" + modifyUser + '\'' +
                '}';
    }
}
