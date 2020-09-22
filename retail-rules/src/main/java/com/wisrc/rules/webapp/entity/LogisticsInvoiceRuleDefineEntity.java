package com.wisrc.rules.webapp.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class LogisticsInvoiceRuleDefineEntity {
    private String ruleId;
    private String ruleName;
    private String offerId;
    private Integer priorityNumber;
    private Date startDate;
    private Date endDate;
    private String remark;
    private Integer statusCd;
    private BigDecimal minTotalAmount;
    private BigDecimal maxTotalAmount;
    private String totalAmountCurrency;
    private BigDecimal minWeight;
    private BigDecimal maxWeight;
    private String modifyUser;
    private Timestamp modifyTime;
    private String createUser;
    private Timestamp createTime;

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public Integer getPriorityNumber() {
        return priorityNumber;
    }

    public void setPriorityNumber(Integer priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    public BigDecimal getMinTotalAmount() {
        return minTotalAmount;
    }

    public void setMinTotalAmount(BigDecimal minTotalAmount) {
        this.minTotalAmount = minTotalAmount;
    }

    public BigDecimal getMaxTotalAmount() {
        return maxTotalAmount;
    }

    public void setMaxTotalAmount(BigDecimal maxTotalAmount) {
        this.maxTotalAmount = maxTotalAmount;
    }

    public String getTotalAmountCurrency() {
        return totalAmountCurrency;
    }

    public void setTotalAmountCurrency(String totalAmountCurrency) {
        this.totalAmountCurrency = totalAmountCurrency;
    }

    public BigDecimal getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(BigDecimal minWeight) {
        this.minWeight = minWeight;
    }

    public BigDecimal getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(BigDecimal maxWeight) {
        this.maxWeight = maxWeight;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogisticsInvoiceRuleDefineEntity that = (LogisticsInvoiceRuleDefineEntity) o;
        return Objects.equals(ruleId, that.ruleId) &&
                Objects.equals(ruleName, that.ruleName) &&
                Objects.equals(offerId, that.offerId) &&
                Objects.equals(priorityNumber, that.priorityNumber) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(statusCd, that.statusCd) &&
                Objects.equals(minTotalAmount, that.minTotalAmount) &&
                Objects.equals(maxTotalAmount, that.maxTotalAmount) &&
                Objects.equals(totalAmountCurrency, that.totalAmountCurrency) &&
                Objects.equals(minWeight, that.minWeight) &&
                Objects.equals(maxWeight, that.maxWeight) &&
                Objects.equals(modifyUser, that.modifyUser) &&
                Objects.equals(modifyTime, that.modifyTime) &&
                Objects.equals(createUser, that.createUser) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ruleId, ruleName, offerId, priorityNumber, startDate, endDate, remark, statusCd, minTotalAmount, maxTotalAmount, totalAmountCurrency, minWeight, maxWeight, modifyUser, modifyTime, createUser, createTime);
    }
}
