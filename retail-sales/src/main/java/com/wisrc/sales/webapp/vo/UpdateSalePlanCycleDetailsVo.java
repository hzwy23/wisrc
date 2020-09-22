package com.wisrc.sales.webapp.vo;

import io.swagger.annotations.ApiModelProperty;

public class UpdateSalePlanCycleDetailsVo {
    @ApiModelProperty(value = "唯一标识")
    private String uuid;
    @ApiModelProperty(value = "销售计划ID", hidden = true)
    private String salePlanId;
    @ApiModelProperty(value = "计划年月", hidden = true)
    private String planDate;
    @ApiModelProperty(value = "周期", hidden = true)
    private int saleCycle;
    @ApiModelProperty(value = "重量", hidden = true)
    private double weight;
    @ApiModelProperty(value = "成本价")
    private double costPrice;
    @ApiModelProperty(value = "售价")
    private double salePrice;
    @ApiModelProperty(value = "预计日销量")
    private int daySaleNum;
    @ApiModelProperty(value = "销售时长")
    private int saleTime;
    @ApiModelProperty(value = "销售额")
    private double saleAmount;
    @ApiModelProperty(value = "预计退款率")
    private double estimateRefundableRate;
    @ApiModelProperty(value = "佣金系数")
    private double commissionCoefficient;
    @ApiModelProperty(value = "佣金")
    private double commission;
    @ApiModelProperty(value = "fulfillmentCost")
    private double fulfillmentCost;
    @ApiModelProperty(value = "营销费用")
    private double marketingCost;
    @ApiModelProperty(value = "营销费用占比")
    private double marketingCostRatio;
    @ApiModelProperty(value = "测评费用")
    private double testCost;
    @ApiModelProperty(value = "测评费用占比")
    private double testCostRatio;
    @ApiModelProperty(value = "广告费用")
    private double advertisementCost;
    @ApiModelProperty(value = "广告费用占比")
    private double advertisementCostRatio;
    @ApiModelProperty(value = "优惠券费用")
    private double couponCost;
    @ApiModelProperty(value = "优惠券费用占比")
    private double couponCostRatio;
    @ApiModelProperty(value = "Deal费用")
    private double dealCost;
    @ApiModelProperty(value = "Deal费用占比")
    private double dealCostRatio;
    @ApiModelProperty(value = "站外推广费用")
    private double outsidePromotionCost;
    @ApiModelProperty(value = "站外推广费用占比")
    private double outsidePromotionCostRatio;
    @ApiModelProperty(value = "头程单价")
    private double firstUnitPrice;
    @ApiModelProperty(value = "头程运费")
    private double firstFreight;
    @ApiModelProperty(value = "破损成本")
    private double breakageCost;
    @ApiModelProperty(value = "实际销售额")
    private double realSaleAmount;
    @ApiModelProperty(value = "总成本费用")
    private double totalCost;
    @ApiModelProperty(value = "毛利润")
    private double grossProfit;
    @ApiModelProperty(value = "毛利率")
    private double grossRate;
    @ApiModelProperty(value = "日均毛利率")
    private double dayGrossRate;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSalePlanId() {
        return salePlanId;
    }

    public void setSalePlanId(String salePlanId) {
        this.salePlanId = salePlanId;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public int getSaleCycle() {
        return saleCycle;
    }

    public void setSaleCycle(int saleCycle) {
        this.saleCycle = saleCycle;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public int getDaySaleNum() {
        return daySaleNum;
    }

    public void setDaySaleNum(int daySaleNum) {
        this.daySaleNum = daySaleNum;
    }

    public int getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(int saleTime) {
        this.saleTime = saleTime;
    }

    public double getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(double saleAmount) {
        this.saleAmount = saleAmount;
    }

    public double getEstimateRefundableRate() {
        return estimateRefundableRate;
    }

    public void setEstimateRefundableRate(double estimateRefundableRate) {
        this.estimateRefundableRate = estimateRefundableRate;
    }

    public double getCommissionCoefficient() {
        return commissionCoefficient;
    }

    public void setCommissionCoefficient(double commissionCoefficient) {
        this.commissionCoefficient = commissionCoefficient;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getFulfillmentCost() {
        return fulfillmentCost;
    }

    public void setFulfillmentCost(double fulfillmentCost) {
        this.fulfillmentCost = fulfillmentCost;
    }

    public double getMarketingCost() {
        return marketingCost;
    }

    public void setMarketingCost(double marketingCost) {
        this.marketingCost = marketingCost;
    }

    public double getMarketingCostRatio() {
        return marketingCostRatio;
    }

    public void setMarketingCostRatio(double marketingCostRatio) {
        this.marketingCostRatio = marketingCostRatio;
    }

    public double getTestCost() {
        return testCost;
    }

    public void setTestCost(double testCost) {
        this.testCost = testCost;
    }

    public double getTestCostRatio() {
        return testCostRatio;
    }

    public void setTestCostRatio(double testCostRatio) {
        this.testCostRatio = testCostRatio;
    }

    public double getAdvertisementCost() {
        return advertisementCost;
    }

    public void setAdvertisementCost(double advertisementCost) {
        this.advertisementCost = advertisementCost;
    }

    public double getAdvertisementCostRatio() {
        return advertisementCostRatio;
    }

    public void setAdvertisementCostRatio(double advertisementCostRatio) {
        this.advertisementCostRatio = advertisementCostRatio;
    }

    public double getCouponCost() {
        return couponCost;
    }

    public void setCouponCost(double couponCost) {
        this.couponCost = couponCost;
    }

    public double getCouponCostRatio() {
        return couponCostRatio;
    }

    public void setCouponCostRatio(double couponCostRatio) {
        this.couponCostRatio = couponCostRatio;
    }

    public double getDealCost() {
        return dealCost;
    }

    public void setDealCost(double dealCost) {
        this.dealCost = dealCost;
    }

    public double getDealCostRatio() {
        return dealCostRatio;
    }

    public void setDealCostRatio(double dealCostRatio) {
        this.dealCostRatio = dealCostRatio;
    }

    public double getOutsidePromotionCost() {
        return outsidePromotionCost;
    }

    public void setOutsidePromotionCost(double outsidePromotionCost) {
        this.outsidePromotionCost = outsidePromotionCost;
    }

    public double getOutsidePromotionCostRatio() {
        return outsidePromotionCostRatio;
    }

    public void setOutsidePromotionCostRatio(double outsidePromotionCostRatio) {
        this.outsidePromotionCostRatio = outsidePromotionCostRatio;
    }

    public double getFirstUnitPrice() {
        return firstUnitPrice;
    }

    public void setFirstUnitPrice(double firstUnitPrice) {
        this.firstUnitPrice = firstUnitPrice;
    }

    public double getFirstFreight() {
        return firstFreight;
    }

    public void setFirstFreight(double firstFreight) {
        this.firstFreight = firstFreight;
    }

    public double getBreakageCost() {
        return breakageCost;
    }

    public void setBreakageCost(double breakageCost) {
        this.breakageCost = breakageCost;
    }

    public double getRealSaleAmount() {
        return realSaleAmount;
    }

    public void setRealSaleAmount(double realSaleAmount) {
        this.realSaleAmount = realSaleAmount;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(double grossProfit) {
        this.grossProfit = grossProfit;
    }

    public double getGrossRate() {
        return grossRate;
    }

    public void setGrossRate(double grossRate) {
        this.grossRate = grossRate;
    }

    public double getDayGrossRate() {
        return dayGrossRate;
    }

    public void setDayGrossRate(double dayGrossRate) {
        this.dayGrossRate = dayGrossRate;
    }
}
