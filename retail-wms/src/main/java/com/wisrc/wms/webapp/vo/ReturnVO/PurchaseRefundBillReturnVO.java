package com.wisrc.wms.webapp.vo.ReturnVO;

import com.wisrc.wms.webapp.vo.SkuInfoVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 采购退货单回写
 * WMS->ERP
 */
public class PurchaseRefundBillReturnVO {
    @ApiModelProperty("退货单号")
    private String returnBill;
    @ApiModelProperty("状态")
    private String deleteStatus;
    @ApiModelProperty("退货商品清单")
    private List<SkuInfoVO> skuEntityList;

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getReturnBill() {
        return returnBill;
    }

    public void setReturnBill(String returnBill) {
        this.returnBill = returnBill;
    }

    public List<SkuInfoVO> getSkuEntityList() {
        return skuEntityList;
    }

    public void setSkuEntityList(List<SkuInfoVO> skuEntityList) {
        this.skuEntityList = skuEntityList;
    }
}
