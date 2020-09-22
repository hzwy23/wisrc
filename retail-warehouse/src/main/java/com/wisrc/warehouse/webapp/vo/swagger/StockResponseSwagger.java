package com.wisrc.warehouse.webapp.vo.swagger;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class StockResponseSwagger {

    @ApiModelProperty(position = 1, value = "编码")
    public int code;
    @ApiModelProperty(position = 2)
    public String msg;
    @ApiModelProperty(position = 3)
    public NStockResponseSwagger data;

    protected class NStockResponseSwagger {
        @ApiModelProperty(position = 1)
        public int total;
        @ApiModelProperty(position = 2)
        public int pages;
        @ApiModelProperty(position = 3)
        public MStockResponseSwagger stockList;
    }

    protected class MStockResponseSwagger {
        public String stockId;
        public String skuId;
        public String skuName;
        public String warehouseId;
        public Integer freezeStockNum;
        public Integer enableStockNum;
        public Date lastInoutTime;
        public Integer statusCd;
        public Integer detailsStatusCd;
        public String mskuId;
        public String shopId;

        public String getStockId() {
            return stockId;
        }

        public void setStockId(String stockId) {
            this.stockId = stockId;
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

        public String getWarehouseId() {
            return warehouseId;
        }

        public void setWarehouseId(String warehouseId) {
            this.warehouseId = warehouseId;
        }

        public Integer getFreezeStockNum() {
            return freezeStockNum;
        }

        public void setFreezeStockNum(Integer freezeStockNum) {
            this.freezeStockNum = freezeStockNum;
        }

        public Integer getEnableStockNum() {
            return enableStockNum;
        }

        public void setEnableStockNum(Integer enableStockNum) {
            this.enableStockNum = enableStockNum;
        }

        public Date getLastInoutTime() {
            return lastInoutTime;
        }

        public void setLastInoutTime(Date lastInoutTime) {
            this.lastInoutTime = lastInoutTime;
        }

        public Integer getStatusCd() {
            return statusCd;
        }

        public void setStatusCd(Integer statusCd) {
            this.statusCd = statusCd;
        }

        public Integer getDetailsStatusCd() {
            return detailsStatusCd;
        }

        public void setDetailsStatusCd(Integer detailsStatusCd) {
            this.detailsStatusCd = detailsStatusCd;
        }

        public String getMskuId() {
            return mskuId;
        }

        public void setMskuId(String mskuId) {
            this.mskuId = mskuId;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }
    }
}
