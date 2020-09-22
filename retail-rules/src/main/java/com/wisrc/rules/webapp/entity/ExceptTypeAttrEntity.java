package com.wisrc.rules.webapp.entity;

import java.util.Objects;

public class ExceptTypeAttrEntity {
    private String exceptTypeCd;
    private String exceptTypeName;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExceptTypeAttrEntity that = (ExceptTypeAttrEntity) o;
        return Objects.equals(exceptTypeCd, that.exceptTypeCd) &&
                Objects.equals(exceptTypeName, that.exceptTypeName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(exceptTypeCd, exceptTypeName);
    }
}
