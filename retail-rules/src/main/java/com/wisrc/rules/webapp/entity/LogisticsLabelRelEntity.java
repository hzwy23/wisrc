package com.wisrc.rules.webapp.entity;

import java.util.Objects;

public class LogisticsLabelRelEntity {
    private String uuid;
    private String ruleId;
    private Integer labelCd;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getLabelCd() {
        return labelCd;
    }

    public void setLabelCd(Integer labelCd) {
        this.labelCd = labelCd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogisticsLabelRelEntity that = (LogisticsLabelRelEntity) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(labelCd, that.labelCd);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, labelCd);
    }
}
