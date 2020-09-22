package com.wisrc.supplier.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 供应商帐号
 *
 * @author linguosheng
 */
public class SupplierAccount {

    @ApiModelProperty(value = "更新必填id ")
    private Integer id;

    @ApiModelProperty(value = "添加必填供应商编号")
    private String sId;

    @ApiModelProperty(value = "收款人", required = true)
    private String payee;

    @ApiModelProperty(value = "银行名称", required = true)
    private String bank;

    @ApiModelProperty(value = "银行支行", required = true)
    private String subbranch;

    @ApiModelProperty(value = "银行帐号", required = true)
    private String account;

    @ApiModelProperty(value = "类型：0对内 1对外", required = true)
    private Integer type;

    @ApiModelProperty(value = "委托书地址", required = true)
    private String diploma;

    @ApiModelProperty(hidden = true)
    private Integer auditStatus;

    @ApiModelProperty(hidden = true)
    private String auditInfo;

    @ApiModelProperty(hidden = true)
    private String mender;

    @ApiModelProperty(hidden = true)
    private Date updateTime;

    @ApiModelProperty(hidden = true)
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
