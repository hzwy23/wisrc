package com.wisrc.replenishment.webapp.dto.mskuSalesPlan;

import com.wisrc.replenishment.webapp.dto.msku.MskuPlanPageDTO;
import lombok.Data;

import java.util.List;

@Data
public class GetSalesPlanDTO {
    List<MskuPlanPageDTO> salesPlan;
}
