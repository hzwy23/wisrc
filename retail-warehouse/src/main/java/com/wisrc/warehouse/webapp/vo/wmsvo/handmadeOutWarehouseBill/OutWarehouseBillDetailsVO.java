package com.wisrc.warehouse.webapp.vo.wmsvo.handmadeOutWarehouseBill;

import io.swagger.annotations.ApiModelProperty;


public class OutWarehouseBillDetailsVO {

    @ApiModelProperty(value = "行号")
    private String lineNum;

    @ApiModelProperty(value = "库存sku")
    private String goodsCode;

    @ApiModelProperty(value = "FnSku")
    private String fnCode;

    @ApiModelProperty(value = "产品名称")
    private String goodsName;

    @ApiModelProperty(value = "个数")
    private String unitQuantity;

    @ApiModelProperty(value = "箱数")
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
