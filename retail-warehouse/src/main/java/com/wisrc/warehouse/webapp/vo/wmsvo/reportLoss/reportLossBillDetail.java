package com.wisrc.warehouse.webapp.vo.wmsvo.reportLoss;

import io.swagger.annotations.ApiModelProperty;

public class reportLossBillDetail {

    @ApiModelProperty(value = "物料编号", required = true)
    private String goodsCode;

    @ApiModelProperty(value = "物料名称", required = true)
    private String goodsName;

    @ApiModelProperty(value = "个数", required = true)
    private Double unitQuantity;

    @ApiModelProperty(value = "箱数", required = true)
    private Double packageQuantity;

    @ApiModelProperty(value = "总数", required = true)
    private Double totalQuantity;

    @ApiModelProperty(value = "生产批次号")
    private String productionBatchNo;

    @ApiModelProperty(value = "行号", required = true)
    private int lineNum;

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

    public Double getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(Double unitQuantity) {
        this.unitQuantity = unitQuantity;
    }

    public Double getPackageQuantity() {
        return packageQuantity;
    }

    public void setPackageQuantity(Double packageQuantity) {
        this.packageQuantity = packageQuantity;
    }

    public Double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getProductionBatchNo() {
        return productionBatchNo;
    }

    public void setProductionBatchNo(String productionBatchNo) {
        this.productionBatchNo = productionBatchNo;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }
}
