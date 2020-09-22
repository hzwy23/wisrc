package com.wisrc.wms.webapp.vo.ReturnVO;

import io.swagger.annotations.ApiModelProperty;

/**
 * 库存回写
 * WMS->ERP
 */
public class StockReturnVO {
    @ApiModelProperty("仓库id")
    private String warehouseId;
    @ApiModelProperty("仓库名称")
    private String warehouseName;
    @ApiModelProperty("分仓id名字")
    private String subWarehouseName;
    @ApiModelProperty("分仓id")
    private String subWarehouseId;
    @ApiModelProperty("库区id")
    private String warehouseZoneId;
    @ApiModelProperty("库区名称")
    private String warehouseZoneName;
    @ApiModelProperty("库位编码")
    private String warehousePositionId;
    @ApiModelProperty("库位名称")
    private String warehousePosition;
    @ApiModelProperty("库存skuId")
    private String skuId;
    @ApiModelProperty("产品名称")
    private String skuName;
    @ApiModelProperty("fnSku")
    private String fnSkuId;
    @ApiModelProperty("入库批次")
    private String enterBatch;
    @ApiModelProperty("生产批次")
    private String productionBatch;
    @ApiModelProperty("总数")
    private Integer sumStock;
    @ApiModelProperty("可用数")
    private Integer enableStockNum;
    @ApiModelProperty("冻结数")
    private Integer freezeStockNum;
    @ApiModelProperty("分配数")
    private Integer assignedNum;
    @ApiModelProperty("待上架数")
    private Integer waitUpNum;
    @ApiModelProperty("补货待下架数")
    private Integer replenishmentWaitDownNum;
    @ApiModelProperty("补货待上架数")
    private Integer replenishmentWaitUpNum;
    @ApiModelProperty("bId")
    private Integer bId;

    public String getWarehouseZoneId() {
        return warehouseZoneId;
    }

    public void setWarehouseZoneId(String warehouseZoneId) {
        this.warehouseZoneId = warehouseZoneId;
    }

    public String getWarehousePositionId() {
        return warehousePositionId;
    }

    public void setWarehousePositionId(String warehousePositionId) {
        this.warehousePositionId = warehousePositionId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getSubWarehouseName() {
        return subWarehouseName;
    }

    public void setSubWarehouseName(String subWarehouseName) {
        this.subWarehouseName = subWarehouseName;
    }

    public String getSubWarehouseId() {
        return subWarehouseId;
    }

    public void setSubWarehouseId(String subWarehouseId) {
        this.subWarehouseId = subWarehouseId;
    }

    public String getWarehouseZoneName() {
        return warehouseZoneName;
    }

    public void setWarehouseZoneName(String warehouseZoneName) {
        this.warehouseZoneName = warehouseZoneName;
    }

    public String getWarehousePosition() {
        return warehousePosition;
    }

    public void setWarehousePosition(String warehousePosition) {
        this.warehousePosition = warehousePosition;
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

    public String getFnSkuId() {
        return fnSkuId;
    }

    public void setFnSkuId(String fnSkuId) {
        this.fnSkuId = fnSkuId;
    }

    public String getEnterBatch() {
        return enterBatch;
    }

    public void setEnterBatch(String enterBatch) {
        this.enterBatch = enterBatch;
    }

    public String getProductionBatch() {
        return productionBatch;
    }

    public void setProductionBatch(String productionBatch) {
        this.productionBatch = productionBatch;
    }

    public Integer getSumStock() {
        return sumStock;
    }

    public void setSumStock(Integer sumStock) {
        this.sumStock = sumStock;
    }

    public Integer getEnableStockNum() {
        return enableStockNum;
    }

    public void setEnableStockNum(Integer enableStockNum) {
        this.enableStockNum = enableStockNum;
    }

    public Integer getFreezeStockNum() {
        return freezeStockNum;
    }

    public void setFreezeStockNum(Integer freezeStockNum) {
        this.freezeStockNum = freezeStockNum;
    }

    public Integer getAssignedNum() {
        return assignedNum;
    }

    public void setAssignedNum(Integer assignedNum) {
        this.assignedNum = assignedNum;
    }

    public Integer getWaitUpNum() {
        return waitUpNum;
    }

    public void setWaitUpNum(Integer waitUpNum) {
        this.waitUpNum = waitUpNum;
    }

    public Integer getReplenishmentWaitDownNum() {
        return replenishmentWaitDownNum;
    }

    public void setReplenishmentWaitDownNum(Integer replenishmentWaitDownNum) {
        this.replenishmentWaitDownNum = replenishmentWaitDownNum;
    }

    public Integer getReplenishmentWaitUpNum() {
        return replenishmentWaitUpNum;
    }

    public void setReplenishmentWaitUpNum(Integer replenishmentWaitUpNum) {
        this.replenishmentWaitUpNum = replenishmentWaitUpNum;
    }

    public Integer getbId() {
        return bId;
    }

    public void setbId(Integer bId) {
        this.bId = bId;
    }


    @Override
    public String toString() {
        return "StockReturnVO{" +
                "warehouseId='" + warehouseId + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", subWarehouseName='" + subWarehouseName + '\'' +
                ", subWarehouseId='" + subWarehouseId + '\'' +
                ", warehouseZoneId='" + warehouseZoneId + '\'' +
                ", warehouseZoneName='" + warehouseZoneName + '\'' +
                ", warehousePositionId='" + warehousePositionId + '\'' +
                ", warehousePosition='" + warehousePosition + '\'' +
                ", skuId='" + skuId + '\'' +
                ", skuName='" + skuName + '\'' +
                ", fnSkuId='" + fnSkuId + '\'' +
                ", enterBatch='" + enterBatch + '\'' +
                ", productionBatch='" + productionBatch + '\'' +
                ", sumStock=" + sumStock +
                ", enableStockNum=" + enableStockNum +
                ", freezeStockNum=" + freezeStockNum +
                ", assignedNum=" + assignedNum +
                ", waitUpNum=" + waitUpNum +
                ", replenishmentWaitDownNum=" + replenishmentWaitDownNum +
                ", replenishmentWaitUpNum=" + replenishmentWaitUpNum +
                ", bId=" + bId +
                '}';
    }
}
