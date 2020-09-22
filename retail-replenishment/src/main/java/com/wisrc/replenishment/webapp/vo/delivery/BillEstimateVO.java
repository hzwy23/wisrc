package com.wisrc.replenishment.webapp.vo.delivery;

import com.wisrc.replenishment.webapp.vo.PackSpecVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 计费估算信息
 */
public class BillEstimateVO {

    @ApiModelProperty(value = "计费类型")
    private String billType;//计费类型
    @ApiModelProperty(value = "计费类型名称")
    private String billTypeName;//计费类型名称
    @ApiModelProperty(value = "计费总重量")
    private String billTotalWeight;//计费总重量
    @ApiModelProperty(value = "装箱规格")
    private List<PackSpecVO> packSpecList;//装箱规格

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillTypeName() {
        return billTypeName;
    }

    public void setBillTypeName(String billTypeName) {
        this.billTypeName = billTypeName;
    }

    public String getBillTotalWeight() {
        return billTotalWeight;
    }

    public void setBillTotalWeight(String billTotalWeight) {
        this.billTotalWeight = billTotalWeight;
    }

    public List<PackSpecVO> getPackSpecList() {
        return packSpecList;
    }

    public void setPackSpecList(List<PackSpecVO> packSpecList) {
        this.packSpecList = packSpecList;
    }

    @Override
    public String toString() {
        return "BillEstimateVO{" +
                "billType='" + billType + '\'' +
                ", billTypeName='" + billTypeName + '\'' +
                ", billTotalWeight='" + billTotalWeight + '\'' +
                ", packSpecList=" + packSpecList +
                '}';
    }
}
