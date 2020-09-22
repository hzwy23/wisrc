package com.wisrc.wms.webapp.vo.ReturnVO;

import com.wisrc.wms.webapp.vo.SkuInfoVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 用于接收WMS那边上架的商品信息
 */
public class SkuPutawayReturnVO {
    @ApiModelProperty("到货通知单号")
    private String arrivalId;
    @ApiModelProperty("仓库id")
    private String warehouseId;
    @ApiModelProperty("上架商品清单")
    private List<SkuInfoVO> arrivalSkuList;


    public List<SkuInfoVO> getArrivalSkuList() {
        return arrivalSkuList;
    }

    public void setArrivalSkuList(List<SkuInfoVO> arrivalSkuList) {
        this.arrivalSkuList = arrivalSkuList;
    }

    public String getArrivalId() {
        return arrivalId;
    }

    public void setArrivalId(String arrivalId) {
        this.arrivalId = arrivalId;
    }


    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }


}
