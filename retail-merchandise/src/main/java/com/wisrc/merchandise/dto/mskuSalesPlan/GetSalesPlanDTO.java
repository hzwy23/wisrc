package com.wisrc.merchandise.dto.mskuSalesPlan;

import com.wisrc.merchandise.dto.msku.MskuPlanPageDTO;
import lombok.Data;

import java.util.List;

@Data
public class GetSalesPlanDTO {
    List<MskuPlanPageDTO> salesPlan;
}
