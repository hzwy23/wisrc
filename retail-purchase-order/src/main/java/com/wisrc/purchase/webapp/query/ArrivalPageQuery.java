package com.wisrc.purchase.webapp.query;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArrivalPageQuery {
    private String arrivalId;
    private Date applyStartDate;
    private Date applyEndDate;
    private String employeeId;
    private Date expectArrivalStartTime;
    private Date expectArrivalEndTime;
    private String orderId;
    private String LogisticsId;
    private String findKey;
    private List supplierIds;
    private List skuIds;
    private Integer statusCd;
}
