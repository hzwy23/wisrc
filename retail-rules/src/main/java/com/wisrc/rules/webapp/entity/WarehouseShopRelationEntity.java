package com.wisrc.rules.webapp.entity;

import java.util.Objects;

public class WarehouseShopRelationEntity {
    private String uuid;
    private String ruleId;
    private String shopId;

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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarehouseShopRelationEntity that = (WarehouseShopRelationEntity) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(shopId, that.shopId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, shopId);
    }
}
