package com.wisrc.purchase.webapp.vo.purchaseReturn.show;

import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class PurchaseReturnInfoVO {
    private String returnBill;
    private String createDateStr;

    private String supplierId;
    private String supplierName;

    private String employeeId;
    private String employeeName;

    private String warehouseId;
    private String warehouseName;

    private String orderId;
    private String remark;

    private Integer statusCd;
    private String statusModifyTime;
    private String packWarehouseId;
    private String packWarehouseName;


    public void setCreateDateStr(Date date) {
        if (date == null) {
            this.createDateStr = "";
        } else {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String str = df.format(date);
            this.createDateStr = str;
        }
        this.createDateStr = createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }


}
