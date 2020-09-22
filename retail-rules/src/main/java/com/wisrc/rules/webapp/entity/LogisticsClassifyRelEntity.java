package com.wisrc.rules.webapp.entity;

import java.util.Objects;

public class LogisticsClassifyRelEntity {
    private String uuid;
    private String ruleId;
    private String classifyCd;

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

    public String getClassifyCd() {
        return classifyCd;
    }

    public void setClassifyCd(String classifyCd) {
        this.classifyCd = classifyCd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogisticsClassifyRelEntity that = (LogisticsClassifyRelEntity) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(classifyCd, that.classifyCd);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, classifyCd);
    }
}
