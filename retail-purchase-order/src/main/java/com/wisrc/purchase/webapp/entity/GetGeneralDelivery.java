package com.wisrc.purchase.webapp.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class GetGeneralDelivery {
    private String skuId;
    private String supplierId;
    private Integer generalDelivery;
    private Date createTime;
    private Integer haulageDays;
    private Integer minimum;
}
