package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.Objects;

public class PurchasePlanRemarkEntity {
    @ApiModelProperty(value = "备注id")
    private String remarkId;
    @ApiModelProperty(value = "采购计划编号")
    private String uuid;
    @ApiModelProperty(value = "备注信息")
    private String remarkDesc;
    @ApiModelProperty(value = "备注人")
    private String createUser;
    @ApiModelProperty(value = "备注时间")
    private Timestamp createTime;

    public String getRemarkId() {
        return remarkId;
    }

    public void setRemarkId(String remarkId) {
        this.remarkId = remarkId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRemarkDesc() {
        return remarkDesc;
    }

    public void setRemarkDesc(String remarkDesc) {
        this.remarkDesc = remarkDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchasePlanRemarkEntity that = (PurchasePlanRemarkEntity) o;
        return Objects.equals(remarkId, that.remarkId) &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(remarkDesc, that.remarkDesc);
    }

    @Override
    public int hashCode() {

        return Objects.hash(remarkId, uuid, remarkDesc);
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
