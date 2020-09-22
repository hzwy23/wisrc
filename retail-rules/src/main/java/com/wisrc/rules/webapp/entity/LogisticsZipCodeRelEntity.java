package com.wisrc.rules.webapp.entity;

import java.util.Objects;

public class LogisticsZipCodeRelEntity {
    private String uuid;
    private String ruleId;
    private String zipCode;

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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogisticsZipCodeRelEntity that = (LogisticsZipCodeRelEntity) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(zipCode, that.zipCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, zipCode);
    }
}
