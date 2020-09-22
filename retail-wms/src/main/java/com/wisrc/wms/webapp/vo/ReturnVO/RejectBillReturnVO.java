package com.wisrc.wms.webapp.vo.ReturnVO;

import com.wisrc.wms.webapp.vo.SkuInfoVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * WMS->ERP
 * 采购拒收单回写实体
 */
public class RejectBillReturnVO {
    @ApiModelProperty("拒收单号")
    private String rejectionId;
    @ApiModelProperty("拒收商品清单")
    private List<SkuInfoVO> skuEntityList;
    @ApiModelProperty("状态")
    private Integer statusCd;

    public String getRejectionId() {
        return rejectionId;
    }

    public void setRejectionId(String rejectionId) {
        this.rejectionId = rejectionId;
    }

    public List<SkuInfoVO> getSkuEntityList() {
        return skuEntityList;
    }

    public void setSkuEntityList(List<SkuInfoVO> skuEntityList) {
        this.skuEntityList = skuEntityList;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }
}
