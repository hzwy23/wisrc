package com.wisrc.purchase.webapp.vo.entrywarehouse;

import com.wisrc.purchase.webapp.entity.EntryWarehouseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;


public class EntryWarehouseVO {
    @ApiModelProperty(value = "采购入库单号")
    private String entryId;
    @ApiModelProperty(value = "入库日期")
    private String entryTime;
    @ApiModelProperty(value = "入库人ID")
    private String entryUser;
    @ApiModelProperty(value = "入库人名称")
    private String employeeName;
    @ApiModelProperty(value = "仓库ID")
    private String warehouseId;
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;
    @ApiModelProperty(value = "验货申请/提货单号")
    private String inspectionId;
    @ApiModelProperty(value = "供应商编号")
    private String supplierCd;
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;
    @ApiModelProperty(value = "供应商送货单号")
    private String supplierDeliveryNum;
    @ApiModelProperty(value = "采购订单号")
    private String orderId;
    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "包材仓库")
    private String packWarehouseId;

    public static final EntryWarehouseVO toVO(EntryWarehouseEntity ele) {
        EntryWarehouseVO vo = new EntryWarehouseVO();
        BeanUtils.copyProperties(ele, vo);
        return vo;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getEntryUser() {
        return entryUser;
    }

    public void setEntryUser(String entryUser) {
        this.entryUser = entryUser;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }


    public String getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getSupplierCd() {
        return supplierCd;
    }

    public void setSupplierCd(String supplierCd) {
        this.supplierCd = supplierCd;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierDeliveryNum() {
        return supplierDeliveryNum;
    }

    public void setSupplierDeliveryNum(String supplierDeliveryNum) {
        this.supplierDeliveryNum = supplierDeliveryNum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getPackWarehouseId() {
        return packWarehouseId;
    }

    public void setPackWarehouseId(String packWarehouseId) {
        this.packWarehouseId = packWarehouseId;
    }

    @Override
    public String toString() {
        return "EntryWarehouseVO{" +
                "entryId='" + entryId + '\'' +
                ", entryTime=" + entryTime +
                ", entryUser='" + entryUser + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", warehouseId='" + warehouseId + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", inspectionId='" + inspectionId + '\'' +
                ", supplierCd='" + supplierCd + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", supplierDeliveryNum='" + supplierDeliveryNum + '\'' +
                ", orderId='" + orderId + '\'' +
                ", remark='" + remark + '\'' +
                ", packWarehouseId='" + packWarehouseId + '\'' +
                '}';
    }
}
