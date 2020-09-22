package com.wisrc.purchase.webapp.dto.purchasePlan;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * PayManInfoDto
 *
 * @author MAJANNING
 * @date 2018/7/13
 */
@Data
@AllArgsConstructor
public class PayManInfoDto {
    String payee;
    String bank;
    String account;
}
