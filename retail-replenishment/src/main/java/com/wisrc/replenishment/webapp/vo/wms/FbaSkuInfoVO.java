package com.wisrc.replenishment.webapp.vo.wms;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class FbaSkuInfoVO {
    @ApiModelProperty("行号")
    private String lineNumber;
    @ApiModelProperty("库存sku")
    private String skuId;
    @ApiModelProperty("fnSku")
    private String fnSkuId;
    @ApiModelProperty("产品名称")
    private String skuName;
    @ApiModelProperty("装箱规格信息")
    private List<ReturnPackInfoVO> packTypeList;

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
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

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public List<ReturnPackInfoVO> getPackTypeList() {
        return packTypeList;
    }

    public void setPackTypeList(List<ReturnPackInfoVO> packTypeList) {
        this.packTypeList = packTypeList;
    }
}
