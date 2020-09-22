package com.wisrc.replenishment.webapp.query;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class GetInspectionPlanByPlanIdsQuery {
    private List<String> commodityIds;
    private Date deliveryPlanStartDate;
    private Date deliveryPlanEndDate;
}
