package com.wisrc.purchase.webapp.vo.swagger;

import com.wisrc.purchase.webapp.dto.inspection.GetInspectionDto;
import com.wisrc.purchase.webapp.utils.Result;
import lombok.Data;

@Data
public class GetInspectionModel extends Result {
    private GetInspectionDto data;
}
