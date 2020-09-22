package com.wisrc.replenishment.webapp.vo.swagger;

import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.dto.logisticsPlan.LogisticsPlanPageDto;
import lombok.Data;

@Data
public class LogisticsPlanPageModel extends Result {
    private LogisticsPlanPageDto data;
}
