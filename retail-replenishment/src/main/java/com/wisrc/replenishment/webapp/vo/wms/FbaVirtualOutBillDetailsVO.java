package com.wisrc.replenishment.webapp.vo.wms;

import io.swagger.annotations.ApiModelProperty;

public class FbaVirtualOutBillDetailsVO {

    @ApiModelProperty(value = "行号")
    private String lineNum;

    @ApiModelProperty(value = "库存SKU")
    private String goodsCode;

    @ApiModelProperty(value = "FnSku", notes = "生产为是时必须传输")
    private String fnSku;

    @ApiModelProperty(value = "生产批次号", notes = "待定")
    private String batch;

    @ApiModelProperty(value = "产品名称")
    private String goodsName;

    @ApiModelProperty(value = "个数")
    private String unitQuantity;

    @ApiModelProperty(value = "箱数", notes = "数据为0")
    private String packageQuantity;

    @ApiModelProperty(value = "总数")
    private String totalQuantity;

    public String getLineNum() {
        return lineNum;
    }

    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getFnSku() {
        return fnSku;
    }

    public void setFnSku(String fnSku) {
        this.fnSku = fnSku;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(String unitQuantity) {
        this.unitQuantity = unitQuantity;
    }

    public String getPackageQuantity() {
        return packageQuantity;
    }

    public void setPackageQuantity(String packageQuantity) {
        this.packageQuantity = packageQuantity;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
