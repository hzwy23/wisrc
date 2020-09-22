package com.wisrc.replenishment.webapp.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class WaybillExcelEntity {
    private String waybillId;
    private String sellCompanyName;
    private String sellContact;
    private String sendAddress;
    private String buyCompanyName;
    private String vatNo;
    private String receiveAddress;
    private String invoiceRemark;
    private Double declareAmount;
    private Date createTime;
}
