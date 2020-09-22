package com.wisrc.replenishment.webapp.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class GetFbaByWaybill {
    private String waybillId;
    private String shipmentId;
    private Date createTime;
    private String commodityId;
    private Integer replenishmentQuantity;
    private Integer deliveryNumber;
    private Integer numberOfBoxes;
    private int outerBoxSpecificationLen;
    private int outerBoxSpecificationWidth;
    private int outerBoxSpecificationHeight;
    private Double packingWeight;
    private Integer packingQuantity;
    private String batchNumber;
}
