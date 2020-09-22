package com.wisrc.wms.webapp.vo.BillSyncVO;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class FbaGoodsInfoVO {
    @ApiModelProperty(value = "行号")
    private int lineNum;
    @ApiModelProperty(value = "库存Sku")
    private String goodsCode;
    @ApiModelProperty(value = "FnSku")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fnCode;
    @ApiModelProperty(value = "产品名称")
    private String goodsName;
    @ApiModelProperty("商品名称")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msku;
    @ApiModelProperty(value = "个数")
    private double unitQuantity;
    @ApiModelProperty(value = "箱数")
    private double packageQuantity;
    @ApiModelProperty(value = "总数")
    private double totalQuantity;
    @ApiModelProperty(value = "装箱信息")
    private List<PackInfoVo> boxGaugeList;

    public String getMsku() {
        return msku;
    }

    public void setMsku(String msku) {
        this.msku = msku;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getFnCode() {
        return fnCode;
    }

    public void setFnCode(String fnCode) {
        this.fnCode = fnCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(double unitQuantity) {
        this.unitQuantity = unitQuantity;
    }

    public double getPackageQuantity() {
        return packageQuantity;
    }

    public void setPackageQuantity(double packageQuantity) {
        this.packageQuantity = packageQuantity;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public List<PackInfoVo> getBoxGaugeList() {
        return boxGaugeList;
    }

    public void setBoxGaugeList(List<PackInfoVo> boxGaugeList) {
        this.boxGaugeList = boxGaugeList;
    }
}
