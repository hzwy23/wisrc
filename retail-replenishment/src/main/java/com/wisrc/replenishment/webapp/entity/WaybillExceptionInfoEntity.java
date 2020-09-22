package com.wisrc.replenishment.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

@Api(tags = "运单异常信息描述")
public class WaybillExceptionInfoEntity {
    @ApiModelProperty(value = "系统唯一标示", hidden = true)
    private String uuid;
    @ApiModelProperty(value = "异常ID")
    private int exceptionTypeCd;
    @ApiModelProperty(value = "运单编码", hidden = true)
    private String waybillId;
    @ApiModelProperty(value = "异常描述名称")
    private String exceptionTypeDesc;
    @ApiModelProperty(value = "删除标示", hidden = true)
    private int deleteStatus;
    @ApiModelProperty(value = "删除时随机码", hidden = true)
    private String deleteRandom;
    @ApiModelProperty(value = "最后修改人", hidden = true)
    private String modifyUser;
    @ApiModelProperty(value = "最后修改时间", hidden = true)
    private Timestamp modifyTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getExceptionTypeCd() {
        return exceptionTypeCd;
    }

    public void setExceptionTypeCd(int exceptionTypeCd) {
        this.exceptionTypeCd = exceptionTypeCd;
    }

    public String getWaybillId() {
        return waybillId;
    }

    public void setWaybillId(String waybillId) {
        this.waybillId = waybillId;
    }

    public String getExceptionTypeDesc() {
        return exceptionTypeDesc;
    }

    public void setExceptionTypeDesc(String exceptionTypeDesc) {
        this.exceptionTypeDesc = exceptionTypeDesc;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getDeleteRandom() {
        return deleteRandom;
    }

    public void setDeleteRandom(String deleteRandom) {
        this.deleteRandom = deleteRandom;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "WaybillExceptionInfoEntity{" +
                "uuid='" + uuid + '\'' +
                ", exceptionTypeCd=" + exceptionTypeCd +
                ", waybillId='" + waybillId + '\'' +
                ", exceptionTypeDesc='" + exceptionTypeDesc + '\'' +
                ", deleteStatus=" + deleteStatus +
                ", deleteRandom='" + deleteRandom + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
