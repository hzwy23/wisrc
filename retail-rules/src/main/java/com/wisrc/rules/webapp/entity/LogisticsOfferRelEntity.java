package com.wisrc.rules.webapp.entity;

import java.util.Objects;

public class LogisticsOfferRelEntity {
    private String uuid;
    private String ruleId;
    private String offerId;

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

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogisticsOfferRelEntity that = (LogisticsOfferRelEntity) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(offerId, that.offerId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, offerId);
    }
}
