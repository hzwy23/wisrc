package com.wisrc.merchandise.service;

import com.wisrc.merchandise.dto.replenishment.VReplenishmentMskuDto;

import java.util.List;
import java.util.Map;

public interface ReplenishmentService {
    Map<String, VReplenishmentMskuDto> getFBAUnderWay(List mskuIds) throws Exception;
}
