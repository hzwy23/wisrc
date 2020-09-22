package com.wisrc.purchase.webapp.vo;

import com.wisrc.purchase.webapp.entity.ProducPackingInfoEntity;
import com.wisrc.purchase.webapp.entity.ProductDeliveryInfoEntity;
import com.wisrc.purchase.webapp.utils.valid.PositiveDouble;
import com.wisrc.purchase.webapp.utils.valid.PositiveMaxFour;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class AddDetailsProdictAllVO {
    @ApiModelProperty(value = "订单产品ID")
    private String id;
    @ApiModelProperty(value = "订单ID")
    private String orderId;
    @ApiModelProperty(value = "产品SKU", position = 1)
    private String skuId;
    @ApiModelProperty(value = "产品名称", position = 1)
    private String skuName;
    @ApiModelProperty(value = "数量", position = 2)
    private int quantity;
    @ApiModelProperty(value = "备品率(%)", position = 3)
    @PositiveDouble(message = "备品率必须为2位小数")
    private double spareRate;
    @ApiModelProperty(value = "不含税单价", position = 4)
    @PositiveMaxFour(message = "不含税单价必须为4位小数以下")
    private double unitPriceWithoutTax;
    @ApiModelProperty(value = "税率(%)", position = 5)
    @PositiveDouble(message = "税率(%)必须为2位小数")
    private double taxRate;
    @ApiModelProperty(value = "含税单价", position = 6)
    @PositiveMaxFour(message = "含税单价必须为4位小数以下")
    private double unitPriceWithTax;
    @ApiModelProperty(value = "不含税金额", position = 7)
    @PositiveDouble(message = "不含税金额必须为2位小数")
    private double amountWithoutTax;
    @ApiModelProperty(value = "含税金额", position = 8)
    @PositiveDouble(message = "含税金额必须为2位小数")
    private double amountWithTax;
    @ApiModelProperty(value = "备注", position = 9)
    private String remark;
    @ApiModelProperty(value = "集装箱信息", position = 10)
    private ProducPackingInfoEntity producPackingInfoEn;
    @ApiModelProperty(value = "交货日期与数量集合信息", position = 11)
    private List<ProductDeliveryInfoEntity> productDeliveryInfoEnList;
    @ApiModelProperty(value = "状态")
    private int status;
    @ApiModelProperty(value = "是否中止")
    private int deleteStatus;
    @ApiModelProperty(value = "采购计划ID")
    private String purchasePlanId;

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSpareRate() {
        return spareRate;
    }

    public void setSpareRate(double spareRate) {
        this.spareRate = spareRate;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ProducPackingInfoEntity getProducPackingInfoEn() {
        return producPackingInfoEn;
    }

    public void setProducPackingInfoEn(ProducPackingInfoEntity producPackingInfoEn) {
        this.producPackingInfoEn = producPackingInfoEn;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<ProductDeliveryInfoEntity> getProductDeliveryInfoEnList() {
        return productDeliveryInfoEnList;
    }

    public void setProductDeliveryInfoEnList(List<ProductDeliveryInfoEntity> productDeliveryInfoEnList) {
        this.productDeliveryInfoEnList = productDeliveryInfoEnList;
    }


    public double getUnitPriceWithoutTax() {
        return unitPriceWithoutTax;
    }

    public void setUnitPriceWithoutTax(double unitPriceWithoutTax) {
        this.unitPriceWithoutTax = unitPriceWithoutTax;
    }

    public double getUnitPriceWithTax() {
        return unitPriceWithTax;
    }

    public void setUnitPriceWithTax(double unitPriceWithTax) {
        this.unitPriceWithTax = unitPriceWithTax;
    }

    public double getAmountWithoutTax() {
        return amountWithoutTax;
    }

    public void setAmountWithoutTax(double amountWithoutTax) {
        this.amountWithoutTax = amountWithoutTax;
    }

    public double getAmountWithTax() {
        return amountWithTax;
    }

    public void setAmountWithTax(double amountWithTax) {
        this.amountWithTax = amountWithTax;
    }

    public String getPurchasePlanId() {
        return purchasePlanId;
    }

    public void setPurchasePlanId(String purchasePlanId) {
        this.purchasePlanId = purchasePlanId;
    }

    @Override
    public String toString() {
        return "AddDetailsProdictAllVO{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", skuId='" + skuId + '\'' +
                ", skuName='" + skuName + '\'' +
                ", quantity=" + quantity +
                ", spareRate=" + spareRate +
                ", unitPriceWithoutTax=" + unitPriceWithoutTax +
                ", taxRate=" + taxRate +
                ", unitPriceWithTax=" + unitPriceWithTax +
                ", amountWithoutTax=" + amountWithoutTax +
                ", amountWithTax=" + amountWithTax +
                ", remark='" + remark + '\'' +
                ", producPackingInfoEn=" + producPackingInfoEn +
                ", productDeliveryInfoEnList=" + productDeliveryInfoEnList +
                '}';
    }
}
