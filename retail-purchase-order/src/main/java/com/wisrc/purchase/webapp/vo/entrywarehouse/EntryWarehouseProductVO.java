package com.wisrc.purchase.webapp.vo.entrywarehouse;

import com.wisrc.purchase.webapp.entity.EntryWarehouseProductEntity;
import com.wisrc.purchase.webapp.utils.valid.PositiveMaxFour;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.Min;

public class EntryWarehouseProductVO {
    @ApiModelProperty(value = "唯一标识")
    private String uuid;
    @ApiModelProperty(value = "采购入库单号")
    private String entryId;
    @ApiModelProperty(value = "SKU")
    private String skuId;
    @ApiModelProperty(value = "SKU中文名称")
    private String skuName;
    @ApiModelProperty(value = "入库数量")
    @Min(value = 0, message = "入库数量需为非负整数")
    private int entryNum;
    @ApiModelProperty(value = "入备品数")
    @Min(value = 0, message = "入备品数需为非负整数")
    private int entryFrets;
    @ApiModelProperty(value = "批次")
    private String batch;
    @ApiModelProperty(value = "不含税单价")
    @PositiveMaxFour(message = "不含税单价必须为4位小数以下")
    private double unitPriceWithoutTax;
    @ApiModelProperty(value = "不含税金额")
    private double amountWithoutTax;
    @ApiModelProperty(value = "税率(%)")
    private double taxRate;
    @ApiModelProperty(value = "含税单价")
    @PositiveMaxFour(message = "含税单价必须为4位小数以下")
    private double unitPriceWithTax;
    @ApiModelProperty(value = "含税金额")
    private double amountWithTax;

    public static final EntryWarehouseProductVO toVO(EntryWarehouseProductEntity ele) {
        EntryWarehouseProductVO vo = new EntryWarehouseProductVO();
        BeanUtils.copyProperties(ele, vo);
        return vo;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
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

    public int getEntryNum() {
        return entryNum;
    }

    public void setEntryNum(int entryNum) {
        this.entryNum = entryNum;
    }

    public int getEntryFrets() {
        return entryFrets;
    }

    public void setEntryFrets(int entryFrets) {
        this.entryFrets = entryFrets;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public double getUnitPriceWithoutTax() {
        return unitPriceWithoutTax;
    }

    public void setUnitPriceWithoutTax(double unitPriceWithoutTax) {
        this.unitPriceWithoutTax = unitPriceWithoutTax;
    }

    public double getAmountWithoutTax() {
        return amountWithoutTax;
    }

    public void setAmountWithoutTax(double amountWithoutTax) {
        this.amountWithoutTax = amountWithoutTax;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public double getUnitPriceWithTax() {
        return unitPriceWithTax;
    }

    public void setUnitPriceWithTax(double unitPriceWithTax) {
        this.unitPriceWithTax = unitPriceWithTax;
    }

    public double getAmountWithTax() {
        return amountWithTax;
    }

    public void setAmountWithTax(double amountWithTax) {
        this.amountWithTax = amountWithTax;
    }

    @Override
    public String toString() {
        return "EntryWarehouseProductVO{" +
                "uuid='" + uuid + '\'' +
                ", entryId='" + entryId + '\'' +
                ", skuId='" + skuId + '\'' +
                ", skuName='" + skuName + '\'' +
                ", entryNum=" + entryNum +
                ", entryFrets=" + entryFrets +
                ", batch=" + batch +
                ", unitPriceWithoutTax=" + unitPriceWithoutTax +
                ", amountWithoutTax=" + amountWithoutTax +
                ", taxRate=" + taxRate +
                ", unitPriceWithTax=" + unitPriceWithTax +
                ", amountWithTax=" + amountWithTax +
                '}';
    }
}
