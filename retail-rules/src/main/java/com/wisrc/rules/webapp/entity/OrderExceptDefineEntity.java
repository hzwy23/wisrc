package com.wisrc.rules.webapp.entity;

import java.util.Objects;

public class OrderExceptDefineEntity {
    private String exceptTypeCd;
    private String exceptTypeName;
    private String condColumn;
    private String condColumnName;
    private String condValue;
    private String description;
    private Integer statusCd;

    public String getExceptTypeCd() {
        return exceptTypeCd;
    }

    public void setExceptTypeCd(String exceptTypeCd) {
        this.exceptTypeCd = exceptTypeCd;
    }

    public String getExceptTypeName() {
        return exceptTypeName;
    }

    public void setExceptTypeName(String exceptTypeName) {
        this.exceptTypeName = exceptTypeName;
    }

    public String getCondColumn() {
        return condColumn;
    }

    public void setCondColumn(String condColumn) {
        this.condColumn = condColumn;
    }

    public String getCondColumnName() {
        return condColumnName;
    }

    public void setCondColumnName(String condColumnName) {
        this.condColumnName = condColumnName;
    }

    public String getCondValue() {
        return condValue;
    }

    public void setCondValue(String condValue) {
        this.condValue = condValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderExceptDefineEntity that = (OrderExceptDefineEntity) o;
        return Objects.equals(exceptTypeCd, that.exceptTypeCd) &&
                Objects.equals(exceptTypeName, that.exceptTypeName) &&
                Objects.equals(condColumn, that.condColumn) &&
                Objects.equals(condColumnName, that.condColumnName) &&
                Objects.equals(condValue, that.condValue) &&
                Objects.equals(description, that.description) &&
                Objects.equals(statusCd, that.statusCd);
    }

    @Override
    public int hashCode() {

        return Objects.hash(exceptTypeCd, exceptTypeName, condColumn, condColumnName, condValue, description, statusCd);
    }
}
