package com.wisrc.wms.webapp.vo.ReturnVO;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TransferOrderPackProductVO {

    @ApiModelProperty(value = "行号")
    private String lineNumber;

    @ApiModelProperty(value = "库存SKU")
    private String skuId;

    @ApiModelProperty(value = "FNSKU")
    private String fnSkuId;

    @ApiModelProperty(value = "产品名称")
    private String skuName;

    List<TransferOrderPackDetailsVO> packTypeList;

    public List<TransferOrderPackDetailsVO> getPackTypeList() {
        return packTypeList;
    }

    public void setPackTypeList(List<TransferOrderPackDetailsVO> packTypeList) {
        this.packTypeList = packTypeList;
    }

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
}