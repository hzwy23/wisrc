package com.wisrc.purchase.webapp.vo.purchaseRejection;

import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class GatherPurchaseRejectionVO {
    private String rejectionId;
    private String handleUser;
    private String inspectionId;
    private String supplierCd;
    private String supplierDeliveryNum;
    private String remark;
    private String deleteStatus;
    private String orderId;
    private String handleUserName;

    private String supplierName;

    private String rejectionDateStr;
    private String uuid;
    private String skuId;
    private String rejectQuantity;
    private String spareQuantity;
    private String batchNumber;
    private String unitPriceWithoutTax;
    private String amountWithoutTax;
    private String taxRate;
    private String unitPriceWithTax;
    private String amountWithTax;
    private String skuNameZh;

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
