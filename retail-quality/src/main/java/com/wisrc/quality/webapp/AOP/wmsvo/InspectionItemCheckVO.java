package com.wisrc.quality.webapp.AOP.wmsvo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class InspectionItemCheckVO {

    @ApiModelProperty(value = "到货通知单号")
    private String voucherCode;

    @ApiModelProperty(value = "采购订单号")
    private String preDeliveryVocuherCode;

    List<InspectionItemCheckDetailsVO> goodsList;

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getPreDeliveryVocuherCode() {
        return preDeliveryVocuherCode;
    }

    public void setPreDeliveryVocuherCode(String preDeliveryVocuherCode) {
        this.preDeliveryVocuherCode = preDeliveryVocuherCode;
    }

    public List<InspectionItemCheckDetailsVO> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<InspectionItemCheckDetailsVO> goodsList) {
        this.goodsList = goodsList;
    }

    @Override
    public String toString() {
        return "InspectionItemCheckVO{" +
                "voucherCode='" + voucherCode + '\'' +
                ", preDeliveryVocuherCode='" + preDeliveryVocuherCode + '\'' +
                ", goodsList=" + goodsList +
                '}';
    }
}
