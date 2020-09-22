package com.wisrc.basic.entity;

import io.swagger.annotations.ApiModelProperty;

public class PackingTypeAttrEntity {
    @ApiModelProperty(value = "包装类型ID(0--全选，1--纸箱（默认），2--其他包装)", required = true)
    private int packingTypeCd;
    @ApiModelProperty(value = "包装类型名称", required = true)
    private String packingTypeName;

    public int getPackingTypeCd() {
        return packingTypeCd;
    }

    public void setPackingTypeCd(int packingTypeCd) {
        this.packingTypeCd = packingTypeCd;
    }

    public String getPackingTypeName() {
        return packingTypeName;
    }

    public void setPackingTypeName(String packingTypeName) {
        this.packingTypeName = packingTypeName;
    }

    @Override
    public String toString() {
        return "PackingTypeAttrEntity{" +
                "packingTypeCd=" + packingTypeCd +
                ", packingTypeName='" + packingTypeName + '\'' +
                '}';
    }
}
