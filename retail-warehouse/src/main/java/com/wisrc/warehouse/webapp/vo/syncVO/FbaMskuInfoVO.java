package com.wisrc.warehouse.webapp.vo.syncVO;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.List;

public class FbaMskuInfoVO {

    @ApiModelProperty(value = "系统唯一标识", hidden = true)
    private String replenishmentCommodityId;
    @ApiModelProperty(value = "商品mskuId", position = 1)
    @NotEmpty(message = "请添加商品信息")
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
    @ApiModelProperty(value = "补货数量")
    private int replenishmentQuantity;
    @ApiModelProperty(value = "装箱数量")
    private int packingNumber;
    @ApiModelProperty(value = "发货数量")
    private int deliveryNumber;
    @ApiModelProperty(value = "签收数量")
    private int signInQuantity;
    private String fnsku;
    private List<FbaMskuPackQueryVO> mskupackList;
    @ApiModelProperty(value = "申报单价")
    private Double declareUnitPrice;
    @ApiModelProperty(value = "单位", hidden = true)
    private int mskuUnitCd;
    @ApiModelProperty(value = "申报要素", hidden = true)
    private String declarationElements;
    //商品信息
    @ApiModelProperty(hidden = true)
    private MskuInfoVO mskuInfoVO;
    @ApiModelProperty("当前商品是否需要加工")
    private int mskuTypeCd;

    public int getMskuTypeCd() {
        return mskuTypeCd;
    }

    public void setMskuTypeCd(int mskuTypeCd) {
        this.mskuTypeCd = mskuTypeCd;
    }

    public int getPackingNumber() {
        return packingNumber;
    }

    public void setPackingNumber(int packingNumber) {
        this.packingNumber = packingNumber;
    }

    public int getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(int deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public String getReplenishmentCommodityId() {
        return replenishmentCommodityId;
    }

    public void setReplenishmentCommodityId(String replenishmentCommodityId) {
        this.replenishmentCommodityId = replenishmentCommodityId;
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

    public String getMskuId() {
        return mskuId;
    }

    public void setMskuId(String mskuId) {
        this.mskuId = mskuId;
    }

    public List<FbaMskuPackQueryVO> getMskupackList() {
        return mskupackList;
    }

    public void setMskupackList(List<FbaMskuPackQueryVO> mskupackList) {
        this.mskupackList = mskupackList;
    }

    public MskuInfoVO getMskuInfoVO() {
        return mskuInfoVO;
    }

    public void setMskuInfoVO(MskuInfoVO mskuInfoVO) {
        this.mskuInfoVO = mskuInfoVO;
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

    public int getSignInQuantity() {
        return signInQuantity;
    }

    public void setSignInQuantity(int signInQuantity) {
        this.signInQuantity = signInQuantity;
    }

    public int getReplenishmentQuantity() {
        return replenishmentQuantity;
    }

    public void setReplenishmentQuantity(int replenishmentQuantity) {
        this.replenishmentQuantity = replenishmentQuantity;
    }

    public String getFnsku() {
        return fnsku;
    }

    public void setFnsku(String fnsku) {
        this.fnsku = fnsku;
    }
}
