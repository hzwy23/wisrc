package com.wisrc.purchase.webapp.vo.purchaseRejection;

import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class PurchaseRejectionInfoVO {

    private String rejectionId;
    private String handleUser;
    private String inspectionId;
    private String supplierCd;
    private String supplierDeliveryNum;
    private String remark;
    private Integer deleteStatus;
    private String orderId;
    private String handleUserName;

    private String supplierName;

    private String rejectionDateStr;

    private String createUser;
    private String createTime;
    private String modifyUser;
    private String modifyTime;
    private Integer statusCd;
    private String statusModifyTime;

    public void setRejectionDateStr(Date time) {
        if (time == null) {
            this.rejectionDateStr = "";
        } else {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String str = df.format(time);
            this.rejectionDateStr = str;
        }
    }

    public void setRejectionDateStr(String rejectionDate) {
        this.rejectionDateStr = rejectionDate;
    }
}
