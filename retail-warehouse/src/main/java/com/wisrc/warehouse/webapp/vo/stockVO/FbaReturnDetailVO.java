package com.wisrc.warehouse.webapp.vo.stockVO;

public class FbaReturnDetailVO {
    private String orderId;
    private String warehouseName;
    private String mskuId;
    private String fnSkuId;
    private int applyReturnNum;
    private int outNum;
    private int signNum;
    private int onwayNum;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
    }

    public String getFnSkuId() {
        return fnSkuId;
    }

    public void setFnSkuId(String fnSkuId) {
        this.fnSkuId = fnSkuId;
    }

    public int getApplyReturnNum() {
        return applyReturnNum;
    }

    public void setApplyReturnNum(int applyReturnNum) {
        this.applyReturnNum = applyReturnNum;
    }

    public int getOutNum() {
        return outNum;
    }

    public void setOutNum(int outNum) {
        this.outNum = outNum;
    }

    public int getSignNum() {
        return signNum;
    }

    public void setSignNum(int signNum) {
        this.signNum = signNum;
    }

    public int getOnwayNum() {
        return onwayNum;
    }

    public void setOnwayNum(int onwayNum) {
        this.onwayNum = onwayNum;
    }
}
