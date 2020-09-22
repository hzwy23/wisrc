package com.wisrc.warehouse.webapp.vo.wmsvo.handmadeEnterWarehouseBill;

import io.swagger.annotations.ApiModelProperty;

public class EnterWarehouseBillDetailsVO {

    @ApiModelProperty("行号")
    private String lineNum;

    @ApiModelProperty("库存SKU")
    private String goodsCode;

    @ApiModelProperty("FnSku")
    private String fnCode;

    @ApiModelProperty("产品名称")
    private String goodsName;

    @ApiModelProperty("个数")
    private String unitQuantity;

    @ApiModelProperty("箱数")
    private String packageQuantity;

    @ApiModelProperty("总数")
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
