package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class PurchasePlanInfoEntity {
    @ApiModelProperty(value = "采购计划编号")
    private String uuid;
    @ApiModelProperty(value = "库存SKU")
    private String skuId;
    @ApiModelProperty(value = "计算日期")
    private Date calculateDate;
    @ApiModelProperty(value = "计划天数")
    private Integer planDay;
    @ApiModelProperty(value = "备货周期")
    private Integer stockCycle;
    @ApiModelProperty(value = "截至销售日")
    private Date endSalesDate;
    @ApiModelProperty(value = "建议采购数")
    private Integer recommendPurchase;
    @ApiModelProperty(value = "最迟采购日期")
    private Date lastPurchaseDate;
    @ApiModelProperty(value = "开始缺货日")
    private Date startOutStock;
    @ApiModelProperty(value = "预计到仓日")
    private Date expectInWarehouse;
    @ApiModelProperty(value = "可用库存")
    private Integer availableStock;
    @ApiModelProperty(value = "预计销量")
    private Integer sumSales;
    @ApiModelProperty(value = "日均销量")
    private Integer avgSales;
    @ApiModelProperty(value = "最低库存量")
    private Integer minStock;
    @ApiModelProperty(value = "通用交期")
    private Integer generalDelivery;
    @ApiModelProperty(value = "国内运输时间")
    private Integer haulageDays;
    @ApiModelProperty(value = "国际运输时间")
    private Integer internationalTransportDays;
    @ApiModelProperty(value = "安全库存天数")
    private Integer safetyStockDays;
    @ApiModelProperty(value = "最少起订量")
    private Integer minimum;
    @ApiModelProperty(value = "供应商编号")
    private String supplierId;
    @ApiModelProperty(value = "状态编码")
    private Integer statusCd;
    @ApiModelProperty(value = "采购计划单号")
    private String purchaseId;
    @ApiModelProperty(value = "采购订单号")
    private String orderId;
    @ApiModelProperty(value = "建议数量")
    private Integer suggestCount;
    @ApiModelProperty(value = "建议日期")
    private Date suggestDate;
    @ApiModelProperty(value = "计算类型编号")
    private Integer calculateTypeCd;
    @ApiModelProperty(value = "计算时间")
    private Timestamp calculateTime;
    @ApiModelProperty(value = "更改人")
    private String modifyUser;
    @ApiModelProperty(value = "备注")
    private String remark;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Date getCalculateDate() {
        return calculateDate;
    }

    public void setCalculateDate(Date calculateDate) {
        this.calculateDate = calculateDate;
    }

    public Integer getPlanDay() {
        return planDay;
    }

    public void setPlanDay(Integer planDay) {
        this.planDay = planDay;
    }

    public Integer getStockCycle() {
        return stockCycle;
    }

    public void setStockCycle(Integer stockCycle) {
        this.stockCycle = stockCycle;
    }

    public Date getEndSalesDate() {
        return endSalesDate;
    }

    public void setEndSalesDate(Date endSalesDate) {
        this.endSalesDate = endSalesDate;
    }

    public Integer getRecommendPurchase() {
        return recommendPurchase;
    }

    public void setRecommendPurchase(Integer recommendPurchase) {
        this.recommendPurchase = recommendPurchase;
    }

    public Date getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public void setLastPurchaseDate(Date lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }

    public Date getStartOutStock() {
        return startOutStock;
    }

    public void setStartOutStock(Date startOutStock) {
        this.startOutStock = startOutStock;
    }

    public Date getExpectInWarehouse() {
        return expectInWarehouse;
    }

    public void setExpectInWarehouse(Date expectInWarehouse) {
        this.expectInWarehouse = expectInWarehouse;
    }

    public Integer getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
    }

    public Integer getSumSales() {
        return sumSales;
    }

    public void setSumSales(Integer sumSales) {
        this.sumSales = sumSales;
    }

    public Integer getAvgSales() {
        return avgSales;
    }

    public void setAvgSales(Integer avgSales) {
        this.avgSales = avgSales;
    }

    public Integer getMinStock() {
        return minStock;
    }

    public void setMinStock(Integer minStock) {
        this.minStock = minStock;
    }

    public Integer getGeneralDelivery() {
        return generalDelivery;
    }

    public void setGeneralDelivery(Integer generalDelivery) {
        this.generalDelivery = generalDelivery;
    }

    public Integer getHaulageDays() {
        return haulageDays;
    }

    public void setHaulageDays(Integer haulageDays) {
        this.haulageDays = haulageDays;
    }

    public Integer getInternationalTransportDays() {
        return internationalTransportDays;
    }

    public void setInternationalTransportDays(Integer internationalTransportDays) {
        this.internationalTransportDays = internationalTransportDays;
    }

    public Integer getSafetyStockDays() {
        return safetyStockDays;
    }

    public void setSafetyStockDays(Integer safetyStockDays) {
        this.safetyStockDays = safetyStockDays;
    }

    public Integer getMinimum() {
        return minimum;
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getSuggestCount() {
        return suggestCount;
    }

    public void setSuggestCount(Integer suggestCount) {
        this.suggestCount = suggestCount;
    }

    public Date getSuggestDate() {
        return suggestDate;
    }

    public void setSuggestDate(Date suggestDate) {
        this.suggestDate = suggestDate;
    }

    public Integer getCalculateTypeCd() {
        return calculateTypeCd;
    }

    public void setCalculateTypeCd(Integer calculateTypeCd) {
        this.calculateTypeCd = calculateTypeCd;
    }

    public Timestamp getCalculateTime() {
        return calculateTime;
    }

    public void setCalculateTime(Timestamp calculateTime) {
        this.calculateTime = calculateTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchasePlanInfoEntity that = (PurchasePlanInfoEntity) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(skuId, that.skuId) &&
                Objects.equals(calculateDate, that.calculateDate) &&
                Objects.equals(planDay, that.planDay) &&
                Objects.equals(stockCycle, that.stockCycle) &&
                Objects.equals(endSalesDate, that.endSalesDate) &&
                Objects.equals(recommendPurchase, that.recommendPurchase) &&
                Objects.equals(lastPurchaseDate, that.lastPurchaseDate) &&
                Objects.equals(startOutStock, that.startOutStock) &&
                Objects.equals(expectInWarehouse, that.expectInWarehouse) &&
                Objects.equals(availableStock, that.availableStock) &&
                Objects.equals(sumSales, that.sumSales) &&
                Objects.equals(avgSales, that.avgSales) &&
                Objects.equals(minStock, that.minStock) &&
                Objects.equals(generalDelivery, that.generalDelivery) &&
                Objects.equals(haulageDays, that.haulageDays) &&
                Objects.equals(internationalTransportDays, that.internationalTransportDays) &&
                Objects.equals(safetyStockDays, that.safetyStockDays) &&
                Objects.equals(minimum, that.minimum) &&
                Objects.equals(supplierId, that.supplierId) &&
                Objects.equals(statusCd, that.statusCd) &&
                Objects.equals(purchaseId, that.purchaseId) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(suggestCount, that.suggestCount) &&
                Objects.equals(suggestDate, that.suggestDate) &&
                Objects.equals(calculateTime, that.calculateTime) &&
                Objects.equals(modifyUser, that.modifyUser);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid, skuId, calculateDate, planDay, stockCycle, endSalesDate, recommendPurchase, lastPurchaseDate, startOutStock, expectInWarehouse, availableStock, sumSales, avgSales, minStock, generalDelivery, haulageDays, internationalTransportDays, safetyStockDays, minimum, supplierId, statusCd, purchaseId, orderId, suggestCount, suggestDate, calculateTime, modifyUser);
    }
}
