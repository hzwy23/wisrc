package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;
import java.util.Objects;

@Data
public class PurchasePlanDetailsEntity {
    private String id;
    @ApiModelProperty(value = "采购计划编号")
    private String uuid;
    @ApiModelProperty(value = "日期")
    private Date planDate;
    @ApiModelProperty(value = "预计销量")
    private Integer expectSales;
    @ApiModelProperty(value = "日均销量")
    private Integer avgSales;
    @ApiModelProperty(value = "可用库存")
    private Integer availableStock;
    @ApiModelProperty(value = "分配后结余")
    private Integer assignBalance;
    @ApiModelProperty(value = "最低库存量")
    private Integer minStock;
    @ApiModelProperty(value = "最少起订量")
    private Integer minimum;
    @ApiModelProperty(value = "建议采购数")
    private Integer suggestPurchase;
    @ApiModelProperty(value = "最迟采购日期")
    private Date lastPurchaseDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Integer getExpectSales() {
        return expectSales;
    }

    public void setExpectSales(Integer expectSales) {
        this.expectSales = expectSales;
    }

    public Integer getAvgSales() {
        return avgSales;
    }

    public void setAvgSales(Integer avgSales) {
        this.avgSales = avgSales;
    }

    public Integer getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
    }

    public Integer getAssignBalance() {
        return assignBalance;
    }

    public void setAssignBalance(Integer assignBalance) {
        this.assignBalance = assignBalance;
    }

    public Integer getMinStock() {
        return minStock;
    }

    public void setMinStock(Integer minStock) {
        this.minStock = minStock;
    }

    public Integer getMinimum() {
        return minimum;
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

    public Integer getSuggestPurchase() {
        return suggestPurchase;
    }

    public void setSuggestPurchase(Integer suggestPurchase) {
        this.suggestPurchase = suggestPurchase;
    }

    public Date getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public void setLastPurchaseDate(Date lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchasePlanDetailsEntity that = (PurchasePlanDetailsEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(planDate, that.planDate) &&
                Objects.equals(expectSales, that.expectSales) &&
                Objects.equals(avgSales, that.avgSales) &&
                Objects.equals(availableStock, that.availableStock) &&
                Objects.equals(assignBalance, that.assignBalance) &&
                Objects.equals(minStock, that.minStock) &&
                Objects.equals(minimum, that.minimum) &&
                Objects.equals(suggestPurchase, that.suggestPurchase) &&
                Objects.equals(lastPurchaseDate, that.lastPurchaseDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, uuid, planDate, expectSales, avgSales, availableStock, assignBalance, minStock, minimum, suggestPurchase, lastPurchaseDate);
    }
}
