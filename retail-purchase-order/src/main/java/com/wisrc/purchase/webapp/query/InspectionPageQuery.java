package com.wisrc.purchase.webapp.query;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class InspectionPageQuery {
    private String arrivalId;
    private Date applyStartDate;
    private Date applyEndDate;
    private String employeeId;
    private Date expectArrivalStartTime;
    private Date expectArrivalEndTime;
    private String orderId;
    private String skuId;
    private String LogisticsId;
    private String findKey;
    private List supplierIds;
    private List skuIds;
    private List arrivalProductIds;
    private Integer statusCd;
}
