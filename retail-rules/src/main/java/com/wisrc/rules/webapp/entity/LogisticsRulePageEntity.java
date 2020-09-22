package com.wisrc.rules.webapp.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class LogisticsRulePageEntity {
    private String ruleId;
    private String ruleName;
    private String offerId;
    private Integer statusCd;
    private Integer priorityNumber;
    private String modifyUser;
    private Timestamp modifyTime;
}
