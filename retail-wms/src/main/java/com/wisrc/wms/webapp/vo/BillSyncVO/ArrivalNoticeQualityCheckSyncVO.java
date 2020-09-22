package com.wisrc.wms.webapp.vo.BillSyncVO;

import com.wisrc.wms.webapp.vo.GoodsInfoVO;

import java.util.List;

public class ArrivalNoticeQualityCheckSyncVO {
    private String voucherCode;
    private String preDeliveryVocuherCode;
    private List<GoodsInfoVO> goodsList;

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

    public List<GoodsInfoVO> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsInfoVO> goodsList) {
        this.goodsList = goodsList;
    }
}
