package com.wisrc.basic.entity;

import lombok.Data;

import java.util.Objects;

@Data
public class ShipmentCompanyCodeAttrEntity {
    private int codeCd;
    private String codeName;
    private String codeUrl;

    public int getCodeCd() {
        return codeCd;
    }

    public void setCodeCd(int codeCd) {
        this.codeCd = codeCd;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShipmentCompanyCodeAttrEntity that = (ShipmentCompanyCodeAttrEntity) o;
        return codeCd == that.codeCd &&
                Objects.equals(codeName, that.codeName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(codeCd, codeName);
    }
}
