package com.wisrc.sys.webapp.bo;

public class WarehouseTypeBO {

    private String typeCd;

    private String typeDesc;

    public String getTypeCd() {
        return typeCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    @Override
    public String toString() {
        return "WarehouseTypeBO{" +
                "typeCd='" + typeCd + '\'' +
                ", typeDesc='" + typeDesc + '\'' +
                '}';
    }
}
