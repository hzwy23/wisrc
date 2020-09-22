package com.wisrc.replenishment.webapp.vo;

import com.wisrc.replenishment.webapp.entity.FbaReplenishmentLabelRelEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 新增补货单信息时接受的实体类
 */
public class FbaInfoAddVO {

    @ApiModelProperty(value = "运单ID", position = 1)
    @NotEmpty(message = "请选择起运仓")
    private String warehouseId;
    @ApiModelProperty("起运仓分仓编码")
    private String subWarehouseId;
    @ApiModelProperty(value = "店铺ID", position = 2)
    @NotEmpty(message = "请选择补货店铺")
    private String shopId;
    @ApiModelProperty(value = "物流报价Id")
    private String offerId;
    @ApiModelProperty(value = "物流商ID", position = 3)
    private String shipmentId;
    @ApiModelProperty(value = "渠道名称", position = 4)
    private String channelName;
    @ApiModelProperty(value = "补货种类", position = 5)
    private int replenishmentSpecies;
    @ApiModelProperty(value = "补货数量", position = 6)
    private int replenishmentCount;
    @ApiModelProperty(value = "备注信息", position = 7)
    private String remark;
    @ApiModelProperty(value = "发货类型", position = 10)
    private int deliveringTypeCd;
    @Valid
    @ApiModelProperty(value = "补货单商品详细信息", position = 8)
    private List<FbaMskuInfoVO> mskuInfoList;
    @ApiModelProperty(value = "标签信息", position = 9)
    private List<FbaReplenishmentLabelRelEntity> labelRelEntityList;

    public String getSubWarehouseId() {
        return subWarehouseId;
    }

    public void setSubWarehouseId(String subWarehouseId) {
        this.subWarehouseId = subWarehouseId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getReplenishmentSpecies() {
        return replenishmentSpecies;
    }

    public void setReplenishmentSpecies(int replenishmentSpecies) {
        this.replenishmentSpecies = replenishmentSpecies;
    }

    public int getReplenishmentCount() {
        return replenishmentCount;
    }

    public void setReplenishmentCount(int replenishmentCount) {
        this.replenishmentCount = replenishmentCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getDeliveringTypeCd() {
        return deliveringTypeCd;
    }

    public void setDeliveringTypeCd(int deliveringTypeCd) {
        this.deliveringTypeCd = deliveringTypeCd;
    }

    public List<FbaMskuInfoVO> getMskuInfoList() {
        return mskuInfoList;
    }

    public void setMskuInfoList(List<FbaMskuInfoVO> mskuInfoList) {
        this.mskuInfoList = mskuInfoList;
    }

    public List<FbaReplenishmentLabelRelEntity> getLabelRelEntityList() {
        return labelRelEntityList;
    }

    public void setLabelRelEntityList(List<FbaReplenishmentLabelRelEntity> labelRelEntityList) {
        this.labelRelEntityList = labelRelEntityList;
    }
}
