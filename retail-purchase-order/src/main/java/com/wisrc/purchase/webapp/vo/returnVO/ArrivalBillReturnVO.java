package com.wisrc.purchase.webapp.vo.returnVO;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 到货通知单回写erp数据
 * WMS->ERP
 */

public class ArrivalBillReturnVO {
    @ApiModelProperty("到货通知单号")
    private String arrivalId;
    @ApiModelProperty("到货商品清单")
    private List<SkuInfoVO> arrivalSkuList;

    public String getArrivalId() {
        return arrivalId;
    }

    public void setArrivalId(String arrivalId) {
        this.arrivalId = arrivalId;
    }

    public List<SkuInfoVO> getArrivalSkuList() {
        return arrivalSkuList;
    }

    public void setArrivalSkuList(List<SkuInfoVO> arrivalSkuList) {
        this.arrivalSkuList = arrivalSkuList;
    }
}
