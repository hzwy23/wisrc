package com.wisrc.rules.webapp.entity;

import java.util.Objects;

public class LogisticsProductRelEntity {
    private String uuid;
    private String ruleId;
    private String skuId;

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

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogisticsProductRelEntity that = (LogisticsProductRelEntity) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(skuId, that.skuId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, skuId);
    }
}
