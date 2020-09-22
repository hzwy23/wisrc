package com.wisrc.warehouse.webapp.vo;

import com.wisrc.warehouse.webapp.entity.WarehouseOutEnterTypeEntity;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

public class OutEnterTypeVO {
    @ApiModelProperty(value = "出入库出入状态类型", required = true)
    private int outEnterType;
    @ApiModelProperty(value = "出入库出入状态名称", required = true)
    private String outEnterName;

    public static final OutEnterTypeVO toVO(WarehouseOutEnterTypeEntity ele) {
        OutEnterTypeVO vo = new OutEnterTypeVO();
        BeanUtils.copyProperties(ele, vo);
        return vo;
    }

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
    public String toString() {
        return "OutEnterTypeVO{" +
                "outEnterType=" + outEnterType +
                ", outEnterName='" + outEnterName + '\'' +
                '}';
    }
}
