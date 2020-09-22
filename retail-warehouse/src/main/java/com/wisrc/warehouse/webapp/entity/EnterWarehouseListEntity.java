package com.wisrc.warehouse.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class EnterWarehouseListEntity {
    @ApiModelProperty(value = "唯一Id", hidden = true)
    private String uuid;
    @ApiModelProperty(value = "入库单编号", hidden = true)
    private String enterBillId;
    @ApiModelProperty(value = "库存sku")
    private String skuId;
    @ApiModelProperty(value = "产品中文名")
    private String skuName;
    @ApiModelProperty("产品图片地址")
    private List<String> imgUrls;
    @ApiModelProperty(value = "FnSKU")
    private String fnSkuId;

    @ApiModelProperty(value = "库位")
    private String warehousePosition;
    @ApiModelProperty(value = "申请入库数量")
    private int enterWarehouseNum;
    @ApiModelProperty("实际入库数量")
    private Integer actualEnterWarehouseNum;

    public Integer getActualEnterWarehouseNum() {
        return actualEnterWarehouseNum;
    }

    public void setActualEnterWarehouseNum(Integer actualEnterWarehouseNum) {
        this.actualEnterWarehouseNum = actualEnterWarehouseNum;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEnterBillId() {
        return enterBillId;
    }

    public void setEnterBillId(String enterBillId) {
        this.enterBillId = enterBillId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getWarehousePosition() {
        return warehousePosition;
    }

    public void setWarehousePosition(String warehousePosition) {
        this.warehousePosition = warehousePosition;
    }

    public int getEnterWarehouseNum() {
        return enterWarehouseNum;
    }

    public void setEnterWarehouseNum(int enterWarehouseNum) {
        this.enterWarehouseNum = enterWarehouseNum;
    }

    public String getFnSkuId() {
        return fnSkuId;
    }

    public void setFnSkuId(String fnSkuId) {
        this.fnSkuId = fnSkuId;
    }

    @Override
    public String toString() {
        return "EnterWarehouseListEntity{" +
                "uuid='" + uuid + '\'' +
                ", enterBillId='" + enterBillId + '\'' +
                ", skuId='" + skuId + '\'' +
                ", skuName='" + skuName + '\'' +
                ", fnSkuId='" + fnSkuId + '\'' +
                ", warehousePosition='" + warehousePosition + '\'' +
                ", enterWarehouseNum=" + enterWarehouseNum +
                '}';
    }
}
