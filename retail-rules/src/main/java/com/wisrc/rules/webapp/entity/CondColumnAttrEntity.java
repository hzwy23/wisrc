package com.wisrc.rules.webapp.entity;

import java.util.Objects;

public class CondColumnAttrEntity {
    private String condColumn;
    private String condColumnName;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CondColumnAttrEntity that = (CondColumnAttrEntity) o;
        return Objects.equals(condColumn, that.condColumn) &&
                Objects.equals(condColumnName, that.condColumnName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(condColumn, condColumnName);
    }
}
