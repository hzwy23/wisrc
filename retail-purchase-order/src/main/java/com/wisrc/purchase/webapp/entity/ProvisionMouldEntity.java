package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

@Api(tags = "订单条款模板")
public class ProvisionMouldEntity {
    @ApiModelProperty(value = "模板唯一标识（新增的时候不传字）")
    private String uuid;
    @ApiModelProperty(value = "条款说明")
    private String explainName;
    @ApiModelProperty(value = "上传附件url")
    private String mouldUrl;
    @ApiModelProperty(value = "删除标识")
    private int deleteStatus;
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;
    @ApiModelProperty(value = "创建人")
    private String createUser;
    @ApiModelProperty(value = "修改人")
    private String modifyUser;
    @ApiModelProperty(value = "修改时间")
    private Timestamp modifyTime;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getExplainName() {
        return explainName;
    }

    public void setExplainName(String explainName) {
        this.explainName = explainName;
    }

    public String getMouldUrl() {
        return mouldUrl;
    }

    public void setMouldUrl(String mouldUrl) {
        this.mouldUrl = mouldUrl;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public String toString() {
        return "ProvisionMouldEntity{" +
                "uuid='" + uuid + '\'' +
                ", explainName='" + explainName + '\'' +
                ", mouldUrl='" + mouldUrl + '\'' +
                ", deleteStatus=" + deleteStatus +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
