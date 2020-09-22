package com.wisrc.purchase.webapp.dto.purchasePlan;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * SkuInfo
 *
 * @author MAJANNING
 * @date 2018/6/30
 */
@Data
@AllArgsConstructor
public class SkuInfo {
    private String skuId;
    private int quantity;
    private String skuName;

    public void decreamQuantity(int i) {
        quantity -= i;
    }

    public void increamQuantity(int i) {
        quantity += i;
    }
}
