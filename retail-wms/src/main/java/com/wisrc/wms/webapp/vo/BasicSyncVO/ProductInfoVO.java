package com.wisrc.wms.webapp.vo.BasicSyncVO;

import java.util.List;

public class ProductInfoVO {
    private String goodsCode;
    private String goodsName;
    private String goodsType;
    private String remark;
    private String declareGoodsName;
    private List<ProductPackInfoVO> goodsPackList;

    public String getDeclareGoodsName() {
        return declareGoodsName;
    }

    public void setDeclareGoodsName(String declareGoodsName) {
        this.declareGoodsName = declareGoodsName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ProductPackInfoVO> getGoodsPackList() {
        return goodsPackList;
    }

    public void setGoodsPackList(List<ProductPackInfoVO> goodsPackList) {
        this.goodsPackList = goodsPackList;
    }
}
