package com.wisrc.purchase.webapp.dto.inspection;

import lombok.Data;

@Data
public class GetQuantityDto {
    private String skuId;
    private Integer quantity;
    private Double spareRate;
}
