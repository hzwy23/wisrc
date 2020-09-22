package com.wisrc.replenishment.webapp.vo.waybill;

import com.wisrc.replenishment.webapp.vo.FbaMskuInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@Api(tags = "物流跟踪单商品信息列表")
public class WaybillMskuInfoVO {
    @ApiModelProperty(value = "补货单号")
    private String fbaReplenishmentId;
    @ApiModelProperty(value = "补货批次")
    private String batchNumber;
    @ApiModelProperty(value = "商铺名称")
    private String shopName;
    @ApiModelProperty(value = "商品信息列表")
    private List<FbaMskuInfoVO> mskuList;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getFbaReplenishmentId() {
        return fbaReplenishmentId;
    }

    public void setFbaReplenishmentId(String fbaReplenishmentId) {
        this.fbaReplenishmentId = fbaReplenishmentId;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }


    public List<FbaMskuInfoVO> getMskuList() {
        return mskuList;
    }

    public void setMskuList(List<FbaMskuInfoVO> mskuList) {
        this.mskuList = mskuList;
    }

}
