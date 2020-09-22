package com.wisrc.rules.webapp.entity;

import java.util.Objects;

public class LogisticsWarehouseRelEntity {
    private String uuid;
    private String ruleId;
    private String warehouseId;

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

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogisticsWarehouseRelEntity that = (LogisticsWarehouseRelEntity) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(warehouseId, that.warehouseId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, warehouseId);
    }
}
