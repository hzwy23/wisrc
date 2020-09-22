package com.wisrc.purchase.webapp.dto.inspection;

import com.wisrc.purchase.webapp.entity.ArrivalBasisInfoEntity;
import com.wisrc.purchase.webapp.entity.ArrivalProductDetailsInfoEntity;
import lombok.Data;

import java.util.List;

@Data
public class InspectionDto extends ArrivalBasisInfoEntity {
    private List<ArrivalProductDetailsInfoEntity> storeSkuGroup;
}
