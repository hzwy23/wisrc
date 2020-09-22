package com.wisrc.shipment.webapp.vo.replenishmentEstimateListVO.get;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class GetReplenishmentEstimateListVO {
    @NotNull
    private List<GetReplenishmentEstimateVO> rRVOlist;
}
