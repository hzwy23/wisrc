package com.wisrc.replenishment.webapp.vo.wms;

import java.util.List;

public class TransferOutProductVO {

    private String boxNumber;

    private String skuId;

    private String fnSkuId;

    private int quantity;

    List<TransferOutPackVO> packTypeList;

    public String getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getFnSkuId() {
        return fnSkuId;
    }

    public void setFnSkuId(String fnSkuId) {
        this.fnSkuId = fnSkuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<TransferOutPackVO> getPackTypeList() {
        return packTypeList;
    }

    public void setPackTypeList(List<TransferOutPackVO> packTypeList) {
        this.packTypeList = packTypeList;
    }
}
