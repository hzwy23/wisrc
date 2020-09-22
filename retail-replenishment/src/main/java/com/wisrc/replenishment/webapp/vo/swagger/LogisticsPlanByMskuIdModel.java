package com.wisrc.replenishment.webapp.vo.swagger;

import com.wisrc.replenishment.webapp.utils.Result;
import com.wisrc.replenishment.webapp.dto.logisticsPlan.LogisticsPlanByMskuIdDto;
import lombok.Data;

import java.util.List;

@Data
public class LogisticsPlanByMskuIdModel extends Result {
    private List<LogisticsPlanByMskuIdDto> data;
}
