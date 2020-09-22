package com.wisrc.replenishment.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.List;

public class FbaNewMskuInfoVO {

    //显示商品详细信息时用
    //补货商品的装箱规格
    List<FbaMskuPackQueryVO> fbaMskuPackUpdateVOList;
    @ApiModelProperty(value = "系统唯一标识", hidden = true)
    private String replenishmentCommodityId;
    @ApiModelProperty(value = "商品mskuId", position = 1)
    private String mskuId;
    @ApiModelProperty(value = "商品Id", position = 2)
    private String commodityId;
    @ApiModelProperty(value = "店铺Id", hidden = true)
    private String shopId;
    @ApiModelProperty(value = "店铺名称", hidden = true)
    private String shopName;
    //修改商品详细信息状态时用
    @ApiModelProperty(value = "补货单Id", hidden = true)
    private String fbaReplenishmentId;
    @ApiModelProperty(value = "修改人", hidden = true)
    private String modifyUser;
    @ApiModelProperty(hidden = true)
    private Timestamp modifyTime;
    @ApiModelProperty(hidden = true)
    private int deleteStatus;
    @ApiModelProperty(value = "发货数量", hidden = true)
    private int deliveryNumber;
    @ApiModelProperty(value = "申报单价")
    private Double declareUnitPrice;
    @ApiModelProperty(value = "单位")
    private int mskuUnitCd;
    @ApiModelProperty(value = "申报要素")
    private String declarationElements;
    //商品信息
    private MskuInfoVO mskuInfoVO;

    public String getReplenishmentCommodityId() {
        return replenishmentCommodityId;
    }

    public void setReplenishmentCommodityId(String replenishmentCommodityId) {
        this.replenishmentCommodityId = replenishmentCommodityId;
    }

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

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

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public List<FbaMskuPackQueryVO> getFbaMskuPackUpdateVOList() {
        return fbaMskuPackUpdateVOList;
    }

    public void setFbaMskuPackUpdateVOList(List<FbaMskuPackQueryVO> fbaMskuPackUpdateVOList) {
        this.fbaMskuPackUpdateVOList = fbaMskuPackUpdateVOList;
    }

    public int getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(int deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public Double getDeclareUnitPrice() {
        return declareUnitPrice;
    }

    public void setDeclareUnitPrice(Double declareUnitPrice) {
        this.declareUnitPrice = declareUnitPrice;
    }

    public int getMskuUnitCd() {
        return mskuUnitCd;
    }

    public void setMskuUnitCd(int mskuUnitCd) {
        this.mskuUnitCd = mskuUnitCd;
    }

    public String getDeclarationElements() {
        return declarationElements;
    }

    public void setDeclarationElements(String declarationElements) {
        this.declarationElements = declarationElements;
    }

    public MskuInfoVO getMskuInfoVO() {
        return mskuInfoVO;
    }

    public void setMskuInfoVO(MskuInfoVO mskuInfoVO) {
        this.mskuInfoVO = mskuInfoVO;
    }
}
