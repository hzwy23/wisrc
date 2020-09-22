package com.wisrc.replenishment.webapp.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class InspectionPlanEntity {
    private String logisticsPlanId;
    private String commodityId;
    private Date deliveryPlanDate;
    private Integer deliveryPlanQuantity;
    private Integer inspectionTrafficDay;
    private Date salesStartTime;
    private Date salesEndTime;
    private Integer salesDemandQuantity;
    private String createUser;
    private Timestamp createTime;
    private String modifyUser;
    private Timestamp modifyTime;
    private Integer deleteStatus;

    public String getLogisticsPlanId() {
        return logisticsPlanId;
    }

    public void setLogisticsPlanId(String logisticsPlanId) {
        this.logisticsPlanId = logisticsPlanId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public Date getDeliveryPlanDate() {
        return deliveryPlanDate;
    }

    public void setDeliveryPlanDate(Date deliveryPlanDate) {
        this.deliveryPlanDate = deliveryPlanDate;
    }

    public Integer getDeliveryPlanQuantity() {
        return deliveryPlanQuantity;
    }

    public void setDeliveryPlanQuantity(Integer deliveryPlanQuantity) {
        this.deliveryPlanQuantity = deliveryPlanQuantity;
    }

    public Integer getInspectionTrafficDay() {
        return inspectionTrafficDay;
    }

    public void setInspectionTrafficDay(Integer inspectionTrafficDay) {
        this.inspectionTrafficDay = inspectionTrafficDay;
    }

    public Date getSalesStartTime() {
        return salesStartTime;
    }

    public void setSalesStartTime(Date salesStartTime) {
        this.salesStartTime = salesStartTime;
    }

    public Date getSalesEndTime() {
        return salesEndTime;
    }

    public void setSalesEndTime(Date salesEndTime) {
        this.salesEndTime = salesEndTime;
    }

    public Integer getSalesDemandQuantity() {
        return salesDemandQuantity;
    }

    public void setSalesDemandQuantity(Integer salesDemandQuantity) {
        this.salesDemandQuantity = salesDemandQuantity;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @Override
    public String toString() {
        return "InspectionPlanEntity{" +
                "logisticsPlanId='" + logisticsPlanId + '\'' +
                ", commodityId='" + commodityId + '\'' +
                ", deliveryPlanDate=" + deliveryPlanDate +
                ", deliveryPlanQuantity=" + deliveryPlanQuantity +
                ", inspectionTrafficDay=" + inspectionTrafficDay +
                ", salesStartTime=" + salesStartTime +
                ", salesEndTime=" + salesEndTime +
                ", salesDemandQuantity=" + salesDemandQuantity +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyTime=" + modifyTime +
                ", deleteStatus=" + deleteStatus +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InspectionPlanEntity that = (InspectionPlanEntity) o;
        return Objects.equals(logisticsPlanId, that.logisticsPlanId) &&
                Objects.equals(commodityId, that.commodityId) &&
                Objects.equals(deliveryPlanDate, that.deliveryPlanDate) &&
                Objects.equals(deliveryPlanQuantity, that.deliveryPlanQuantity) &&
                Objects.equals(inspectionTrafficDay, that.inspectionTrafficDay) &&
                Objects.equals(salesStartTime, that.salesStartTime) &&
                Objects.equals(salesEndTime, that.salesEndTime) &&
                Objects.equals(salesDemandQuantity, that.salesDemandQuantity) &&
                Objects.equals(createUser, that.createUser) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(modifyUser, that.modifyUser) &&
                Objects.equals(modifyTime, that.modifyTime) &&
                Objects.equals(deleteStatus, that.deleteStatus);
    }

    @Override
    public int hashCode() {

        return Objects.hash(logisticsPlanId, commodityId, deliveryPlanDate, deliveryPlanQuantity, inspectionTrafficDay, salesStartTime, salesEndTime, salesDemandQuantity, createUser, createTime, modifyUser, modifyTime, deleteStatus);
    }
}
