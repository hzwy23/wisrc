package com.wisrc.sales.webapp.query;

import lombok.Data;

import java.sql.Date;

@Data
public class GetEstimateApprovalQuery {
    private String estimateIds;
    private Date asOfDate;
}
