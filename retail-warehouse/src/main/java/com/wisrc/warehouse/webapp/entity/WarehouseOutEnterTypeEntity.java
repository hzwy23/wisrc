package com.wisrc.warehouse.webapp.entity;

import java.util.Objects;

public class WarehouseOutEnterTypeEntity {
    private int outEnterType;
    private String outEnterName;

    public int getOutEnterType() {
        return outEnterType;
    }

    public void setOutEnterType(int outEnterType) {
        this.outEnterType = outEnterType;
    }

    public String getOutEnterName() {
        return outEnterName;
    }

    public void setOutEnterName(String outEnterName) {
        this.outEnterName = outEnterName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarehouseOutEnterTypeEntity that = (WarehouseOutEnterTypeEntity) o;
        return outEnterType == that.outEnterType &&
                Objects.equals(outEnterName, that.outEnterName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(outEnterType, outEnterName);
    }

    @Override
    public String toString() {
        return "WarehouseOutEnterTypeEntity{" +
                "outEnterType=" + outEnterType +
                ", outEnterName='" + outEnterName + '\'' +
                '}';
    }
}
