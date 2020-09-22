package com.wisrc.supplier.webapp.controller.swagger;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class SupplierAccountModel {

    @ApiModelProperty(value = "帐号编号")
    private Integer id;

    @ApiModelProperty(value = "供应商编号")
    private String sId;

    @ApiModelProperty(value = "收款人")
    private String payee;

    @ApiModelProperty(value = "银行名称")
    private String bank;

    @ApiModelProperty(value = "银行支行")
    private String subbranch;

    @ApiModelProperty(value = "银行帐号")
    private String account;

    @ApiModelProperty(value = "类型：0对内 1对外")
    private Integer type;

    @ApiModelProperty(value = "委托书图片")
    private String diploma;

    @ApiModelProperty(value = "审核状态：1未通过 2审核通过")
    private Integer auditStatus;

    @ApiModelProperty(value = "审核信息")
    private String auditInfo;

    @ApiModelProperty(value = "创建人")
    private String mender;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getSubbranch() {
        return subbranch;
    }

    public void setSubbranch(String subbranch) {
        this.subbranch = subbranch;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDiploma() {
        return diploma;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditInfo() {
        return auditInfo;
    }

    public void setAuditInfo(String auditInfo) {
        this.auditInfo = auditInfo;
    }

    public String getMender() {
        return mender;
    }

    public void setMender(String mender) {
        this.mender = mender;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
