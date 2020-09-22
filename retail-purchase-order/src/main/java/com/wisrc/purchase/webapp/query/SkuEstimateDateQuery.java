package com.wisrc.purchase.webapp.query;

import lombok.Data;

import java.sql.Date;

@Data
public class SkuEstimateDateQuery {
    private String skuId;
    private Date estimateDate;
}
