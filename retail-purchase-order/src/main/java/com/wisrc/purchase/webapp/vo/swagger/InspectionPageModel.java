package com.wisrc.purchase.webapp.vo.swagger;

import com.wisrc.purchase.webapp.dto.inspection.InspectionPageDto;
import com.wisrc.purchase.webapp.utils.Result;
import lombok.Data;

@Data
public class InspectionPageModel extends Result {
    private InspectionPageDto data;
}
