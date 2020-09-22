package com.wisrc.replenishment.webapp.vo.delivery;

import com.wisrc.replenishment.webapp.vo.FbaLabelVO;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.List;

/**
 * 物流交运补货单信息（头部）
 */
public class FbaDeliveryVO {

    @ApiModelProperty(value = "标签集合")
    List<FbaLabelVO> labelRelEntityList;
    @ApiModelProperty(value = "补货单Id")
    private String fbaReplenishmentId;
    @ApiModelProperty(value = "创建日期")
    private Timestamp createDate;
    @ApiModelProperty(value = "补货批次")
    private String replenishmentBatch;
    @ApiModelProperty(value = "店铺名称")
    private String shopName;
    @ApiModelProperty(value = "发货仓名称")
    private String warehouseName;//发货仓名称
    @ApiModelProperty(value = "收货仓省份代码")
    private String provinceCode;//收货仓省份代码
    @ApiModelProperty(value = "收货仓邮编")
    private String zipCode;//收货仓邮编
    @ApiModelProperty(value = "物流商-渠道名称")
    private String shipmentChannel;

    public String getFbaReplenishmentId() {
        return fbaReplenishmentId;
    }

    public void setFbaReplenishmentId(String fbaReplenishmentId) {
        this.fbaReplenishmentId = fbaReplenishmentId;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getReplenishmentBatch() {
        return replenishmentBatch;
    }

    public void setReplenishmentBatch(String replenishmentBatch) {
        this.replenishmentBatch = replenishmentBatch;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public List<FbaLabelVO> getLabelRelEntityList() {
        return labelRelEntityList;
    }

    public void setLabelRelEntityList(List<FbaLabelVO> labelRelEntityList) {
        this.labelRelEntityList = labelRelEntityList;
    }

    public String getShipmentChannel() {
        return shipmentChannel;
    }

    public void setShipmentChannel(String shipmentChannel) {
        this.shipmentChannel = shipmentChannel;
    }
}
