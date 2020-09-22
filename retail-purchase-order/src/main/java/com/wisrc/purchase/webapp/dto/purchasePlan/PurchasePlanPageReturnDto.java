package com.wisrc.purchase.webapp.dto.purchasePlan;

import com.wisrc.purchase.webapp.dto.PageDto;
import lombok.Data;

import java.util.List;

@Data
public class PurchasePlanPageReturnDto extends PageDto {
    List<PurchasePlanReturnDto> purchasePlans;
}
