package com.wisrc.rules.webapp.entity;

import java.util.Objects;

public class LogisticsCountryRelEntity {
    private String uuid;
    private String ruleId;
    private String countryCd;

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

    public String getCountryCd() {
        return countryCd;
    }

    public void setCountryCd(String countryCd) {
        this.countryCd = countryCd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogisticsCountryRelEntity that = (LogisticsCountryRelEntity) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(countryCd, that.countryCd);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, countryCd);
    }
}
