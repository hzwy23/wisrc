package com.wisrc.purchase.webapp.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class GetEstimateNum {
    private String skuId;
    private Integer estimateNumber;
    private Date estimateDate;
}
