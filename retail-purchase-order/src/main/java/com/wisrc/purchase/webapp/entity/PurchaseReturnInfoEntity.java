package com.wisrc.purchase.webapp.entity;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class PurchaseReturnInfoEntity {
    private String returnBill;
    private Date createDate;
    private String supplierId;
    private String employeeId;
    private String warehouseId;
    private String orderId;
    private String remark;
    private Integer deleteStatus;
    private String createUser;
    private Timestamp createTime;
    private String modifyUser;
    private Timestamp modifyTime;

    private Date createDateStart;
    private Date createDateEnd;

    private Integer statusCd;
    private Timestamp statusModifyTime;
    private String packWarehouseId;

    @Override
    public String toString() {
        return "PurchaseReturnInfoEntity{" +
                "returnBill='" + returnBill + '\'' +
                ", createDate=" + createDate +
                ", supplierId='" + supplierId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", warehouseId='" + warehouseId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", remark='" + remark + '\'' +
                ", deleteStatus=" + deleteStatus +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", modifyUser='" + modifyUser + '\'' +
                ", modifyTime=" + modifyTime +
                ", createDateStart=" + createDateStart +
                ", createDateEnd=" + createDateEnd +
                ", statusCd=" + statusCd +
                ", packWarehouseId='" + packWarehouseId + '\'' +
                '}';
    }
}