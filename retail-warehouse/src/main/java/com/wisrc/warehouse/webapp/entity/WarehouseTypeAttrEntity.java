package com.wisrc.warehouse.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

public class WarehouseTypeAttrEntity {
    @ApiModelProperty(value = "仓库类型编码", required = true)
    private String typeCd;
    @ApiModelProperty(value = "仓库类型名称", required = true)
    private String typeName;

    public String getTypeCd() {
        return typeCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }

    public String getTypeDesc() {
        return typeName;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeName = typeDesc;
    }

}
