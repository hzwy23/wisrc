package com.wisrc.rules.webapp.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class WarehouseRuleDefineEntity {
    private String ruleId;
    private String ruleName;
    private Integer priorityNumber;
    private String warehouseId;
    private String remark;
    private Integer statusCd;
    private Date startDate;
    private Date endDate;
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

    public Integer getPriorityNumber() {
        return priorityNumber;
    }

    public void setPriorityNumber(Integer priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
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
        WarehouseRuleDefineEntity that = (WarehouseRuleDefineEntity) o;
        return Objects.equals(ruleId, that.ruleId) &&
                Objects.equals(ruleName, that.ruleName) &&
                Objects.equals(priorityNumber, that.priorityNumber) &&
                Objects.equals(warehouseId, that.warehouseId) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(modifyUser, that.modifyUser) &&
                Objects.equals(modifyTime, that.modifyTime) &&
                Objects.equals(createUser, that.createUser) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ruleId, ruleName, priorityNumber, warehouseId, remark, startDate, endDate, modifyUser, modifyTime, createUser, createTime);
    }
}
