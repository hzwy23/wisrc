package com.wisrc.wms.webapp.vo.ReturnVO;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TransferOutBasicVO {

    @ApiModelProperty(value = "调拨单号")
    private String transferOrderId;

    @ApiModelProperty(value = "总箱数")
    private String totalNumberOfBoxs;

    @ApiModelProperty(value = "总数量")
    private String totalQuantity;

    List<TransferOutProductVO> skuList;

    public String getTransferOrderId() {
        return transferOrderId;
    }

    public void setTransferOrderId(String transferOrderId) {
        this.transferOrderId = transferOrderId;
    }

    public String getTotalNumberOfBoxs() {
        return totalNumberOfBoxs;
    }

    public void setTotalNumberOfBoxs(String totalNumberOfBoxs) {
        this.totalNumberOfBoxs = totalNumberOfBoxs;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public List<TransferOutProductVO> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<TransferOutProductVO> skuList) {
        this.skuList = skuList;
    }
}
