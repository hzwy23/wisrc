package com.wisrc.purchase.webapp.entity;

import lombok.Data;

import java.sql.Time;

@Data
public class GetScheduledTime {
    private int calculateCycleCd;
    private String calculateCycleWeekAttr;
    private Time datetime;
}
