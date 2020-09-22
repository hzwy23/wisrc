package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

@Api(tags = "物流跟踪记录信息")
public class LogisticsTrackInfoEntity {
    @ApiModelProperty(value = "物流单号", hidden = true)
    private String logisticsId;
    private String signTime;
    private int status;
    @ApiModelProperty(value = "时间")
    private Timestamp recordTime;
    @ApiModelProperty(value = "描述信息")
    private String description;

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public Timestamp getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Timestamp recordTime) {
        this.recordTime = recordTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "LogisticsTrackInfoEntity{" +
                "logisticsId='" + logisticsId + '\'' +
                ", recordTime=" + recordTime +
                ", description='" + description + '\'' +
                '}';
    }
}
