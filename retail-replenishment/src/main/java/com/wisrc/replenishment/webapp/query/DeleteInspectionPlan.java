package com.wisrc.replenishment.webapp.query;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DeleteInspectionPlan {
    private String logisticsPlanId;
    private String modifyUser;
    private Timestamp modifyTime;
}
