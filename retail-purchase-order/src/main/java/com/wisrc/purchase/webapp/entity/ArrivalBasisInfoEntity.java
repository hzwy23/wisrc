package com.wisrc.purchase.webapp.entity;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class ArrivalBasisInfoEntity {
    @ApiModelProperty(value = "到货通知单ID")
    private String arrivalId;
    @ApiModelProperty(value = "申请日期")
    private Date applyDate;
    @ApiModelProperty(value = "供应商ID")
    private String supplierId;
    @ApiModelProperty(value = "采购订单ID")
    private String purchaseOrderId;
    @ApiModelProperty(value = "发起人（员工号）")
    private String employeeId;
    @ApiModelProperty(value = "运输时间-国内")
    private Integer haulageDays;
    @ApiModelProperty(value = "车牌号")
    private String plateNumber;
    @ApiModelProperty(value = "运费")
    private BigDecimal freight;
    @ApiModelProperty(value = "运费分摊原则id")
    private Integer freightApportionCd;
    @ApiModelProperty(value = "预计到货日期")
    private Date estimateArrivalDate;
    @ApiModelProperty(value = "物流单号")
    private String logisticsId;
    @ApiModelProperty(value = "备注信息")
    private String remark;
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;
    @ApiModelProperty(value = "创建人")
    private String createUser;
    @ApiModelProperty(value = "修改时间")
    private Timestamp modifyTime;
    @ApiModelProperty(value = "修改人")
    private String modifyUser;
    @ApiModelProperty(value = "删除标识符 0：正常，1：删除")
    private Integer deleteStatus;
    @ApiModelProperty(value = "到货单状态,默认为1")
    private Integer statusCd;
    @ApiModelProperty(value = "收货仓库")
    private String arrivalWarehouseId;
    @ApiModelProperty(value = "包材仓库")
    private String packWarehouseId;


    public String getArrivalId() {
        return arrivalId;
    }

    public void setArrivalId(String arrivalId) {
        this.arrivalId = arrivalId;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getHaulageDays() {
        return haulageDays;
    }

    public void setHaulageDays(Integer haulageDays) {
        this.haulageDays = haulageDays;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public Integer getFreightApportionCd() {
        return freightApportionCd;
    }

    public void setFreightApportionCd(Integer freightApportionCd) {
        this.freightApportionCd = freightApportionCd;
    }

    public Date getEstimateArrivalDate() {
        return estimateArrivalDate;
    }

    public void setEstimateArrivalDate(Date estimateArrivalDate) {
        this.estimateArrivalDate = estimateArrivalDate;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Integer getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Integer statusCd) {
        this.statusCd = statusCd;
    }


    public String getArrivalWarehouseId() {
        return arrivalWarehouseId;
    }

    public void setArrivalWarehouseId(String arrivalWarehouseId) {
        this.arrivalWarehouseId = arrivalWarehouseId;
    }

    public String getPackWarehouseId() {
        return packWarehouseId;
    }

    public void setPackWarehouseId(String packWarehouseId) {
        this.packWarehouseId = packWarehouseId;
    }

    @Override
    public String toString() {
        return "ArrivalBasisInfoEntity{" +
                "arrivalId='" + arrivalId + '\'' +
                ", applyDate=" + applyDate +
                ", supplierId='" + supplierId + '\'' +
                ", purchaseOrderId='" + purchaseOrderId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", haulageDays=" + haulageDays +
                ", plateNumber='" + plateNumber + '\'' +
                ", freight=" + freight +
                ", freightApportionCd=" + freightApportionCd +
                ", estimateArrivalDate=" + estimateArrivalDate +
                ", logisticsId='" + logisticsId + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", modifyTime=" + modifyTime +
                ", modifyUser='" + modifyUser + '\'' +
                ", deleteStatus=" + deleteStatus +
                ", statusCd=" + statusCd +
                '}';
    }
}
